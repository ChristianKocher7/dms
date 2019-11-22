<#
	.NOTES
	===================================================================================
	 Created on:   	29/05/2019
	 Created by:   	Christof Jungo (SecIntel GmbH)
	 Requirements: 	Logon Script in User Context
	===================================================================================
	.DESCRIPTION
		This powershell script automates the inventory of ARGECPC. It' a simple
        login script which collect the system information and write it to a
        centralised Excel file. The script doesn't run if a sysadmin login
        is detected

        The script recognize the following use cases:
        1) no changes has been detected -> update timestamp
        2) there is an unauthorised workplace change has taken place
        3) an existing workplace has been restaged for an existing user
        4) an existing workplace has been restaged for a new user
        5) a new workplace have been added to the ARGECPC Domain could come from stock
#>
Function UpdateTeamViewerDevice {
    Param(
        [parameter(Mandatory=$true)]
        [string]$TVID,
        [string]$ComputerName,
        [string]$UserName,
        [string]$Token,
        [string]$Description
    )

    $Alias = $ComputerName + " ("+$UserName+")"
    $ContetType = 'application/json; charset=utf-8'
    $RemoteControlID = "r" + $TVID
    $GroupName ="ARGECPC"

    #Define Auth Token
    $bearer = "Bearer",$token
    $header = New-Object "System.Collections.Generic.Dictionary[[String],[String]]"
    $header.Add("authorization", $bearer)

    $BaseURI = 'https://webapi.teamviewer.com/'
    $DeviceURI = 'api/v1/devices/'
    $GroupURI = 'api/v1/groups/'

    #Get Group ID for ARGECPC
    $Result = Invoke-RestMethod -Method Get -Uri $BaseURI$GroupURI -Headers $header -ContentType $ContetType -ErrorVariable TVError -ErrorAction SilentlyContinue
    $TeamviewerGroupList = $Result
    $TeamviewerGroup = $TeamviewerGroupList.groups | where {$_.name -eq $GroupName}
    $TeamviewerGroupID = $TeamviewerGroup.id

    #Determine Device ID from Teamviewer ID
    $Result = Invoke-RestMethod -Method Get -Uri $BaseURI$DeviceURI -Headers $header -ContentType $ContetType -ErrorVariable TVError -ErrorAction SilentlyContinue
    $TeamviewerDeviceList = $Result

    $TeamviewerDevice = $TeamviewerDeviceList.devices | where {$_.remotecontrol_id -eq $RemoteControlID}
    $TeamviewerDeviceID = $TeamviewerDevice.device_id

    If ($TeamviewerDeviceID -eq $null) {
        $ReqURI = $BaseURI+$DeviceURI + $TeamviewerDeviceID
        $Jsonbody= @{
            remotecontrol_id = $RemoteControlID
            alias = $Alias
            groupid = $TeamViewerGroupID
            description = $Description
        } | ConvertTo-Json
        $Response = Invoke-RestMethod -Header $header -Method Post -ContentType $ContetType -Uri $ReqURI -Body $Jsonbody
    }
    Else {
        $ReqURI = $BaseURI+$DeviceURI + $TeamviewerDeviceID
        $Jsonbody= @{
            alias = $Alias
            groupid = $GroupID
        } | ConvertTo-Json
        $Response = Invoke-RestMethod -Header $header -Method PUT -ContentType $ContetType -Uri $ReqURI -Body $Jsonbody
    }
}

Function Get-TeamViewerID {
    param (
        [parameter(Mandatory=$true)]
        [string] $Hostname)

    $ClientID = $null
    $Target = $Hostname
    If (!$Target) {$Target = $env:COMPUTERNAME}

    #Suppresses errors (comment to disable error suppression)
    $ErrorActionPreference = "SilentlyContinue"

    #Attempts to pull clientID value from remote registry and display it if successful
    $RegCon = [Microsoft.Win32.RegistryKey]::OpenRemoteBaseKey('LocalMachine', $Target)
    $RegKey= $RegCon.OpenSubKey("SOFTWARE\\WOW6432Node\\TeamViewer")
    $ClientID = $RegKey.GetValue("clientID")

    If (!$ClientID) {
        $RegKey= $RegCon.OpenSubKey("SOFTWARE\\WOW6432Node\\TeamViewer\Version9")
        $ClientID = $RegKey.GetValue("clientID")
    }

    #If previous attempt was unsuccessful, attempts the same from a different location
    If (!$ClientID) {
        $RegKey= $RegCon.OpenSubKey("SOFTWARE\\TeamViewer")
        $ClientID = $RegKey.GetValue("clientID")
    }
    Return $ClientID
}

Function SendMail {
    param (
        [parameter(Mandatory=$true)]
        [string]$NewUser,
        [string]$OldUser,
        [string]$Hostname)
    # Define the Message Subject and Body
    $MessageSubject = "Information: MultiUser Login"
    $MessageBody = "Benutzer " + $NewUser + " hat sich an " + $Hostname + " angemeldet. Vorher war " + $OldUser + " angemeldet."

    # Define eMail Parameter values
    $MailEncodingutf8 = New-Object System.Text.utf8encoding
    $MailServer = "mail.argecpc.ch"
    $SMTPClient = New-Object Net.Mail.SmtpClient($MailServer, 465)
    $SMTPClient.EnableSsl = $true
    $emailMessage = New-Object System.Net.Mail.MailMessage
    $emailMessage.From = "christof.jungo@argecpc.ch"
    #$emailMessage.To.Add("dave.ast@argecpc.ch")
    $emailMessage.To.Add("christof.jungo@secintel.ch")
    $emailMessage.CC.Add("christof.jungo@bluewin.ch")
    $emailMessage.Subject = $MessageSubject
    $emailMessage.IsBodyHtml = $true
    $emailMessage.Body = $MessageBody
    $SMTPClient.Send($emailMessage)
}

Function Get-HP-Warranty {
    param (
    <#
 You need to first create an app for the Warranty API product.
 1) To do that, you first have to enroll as a developer in the Customer Service and Support group.
 https://developers.hp.com/css-enroll
 2) Then you'll be able to view the documentation at https://developers.hp.com/css/api/product-warranty-api-0
 3) Then you'll be able to create your app at https://developers.hp.com/user/me/my-apps.
 #>

        [parameter(Mandatory=$true)]
        [string]$SerialNumber,
        [string]$Warranty)

    $authKey = Invoke-WebRequest -Method POST -uri "https://css.api.hp.com/oauth/v1/token" -Body "apiKey=xxxx&apiSecret=yyyy&grantType=client_credentials&scope=warranty"
    $Content = ""
    $Content = $authKey.Content
    [string]$AccessToken = $Content.root.access_token | Out-String

    $headers = @{}
    $headers.add("Authorization","Bearer $AccessToken")
    $headers.add("accept","application/json")
    $headers.add("content-type","application/json")
    $body = "[{ `"sn`": $SerialNumber }]"
    $results = Invoke-WebRequest -Method Post -Uri "https://css.api.hp.com/productWarranty/v1/queries" -Headers $headers -Body $body
    $results.Content | ConvertFrom-Json
}

Function Test-FileLock {
    # Test if the file exists and is already open (RO Access is always possible)
    param (
        [parameter(Mandatory=$true)]
        [string]$Path)

    $oFile = New-Object System.IO.FileInfo $Path

    if ((Test-Path -Path $Path) -eq $false) {
        return $false
    }

    try {
        $oStream = $oFile.Open([System.IO.FileMode]::Open, [System.IO.FileAccess]::ReadWrite, [System.IO.FileShare]::None)

        if ($oStream) {
            $oStream.Close()
        }
        $false
    } catch {
        # file is locked by a process.
        return $true
    }
}

Function Test-Access ([string]$DomainName,[string]$FilePath) {
    # The function tests the connectivity and the file access
    # Test Connectivity
    #   if no network connection to the domain is found then exit)
    # Test File-Access
    #   if the inventory file is accessible in readonly then wait between 1 to 30s(random) and retry)
    #   if inbetween the user loose network connection then exit
    If (Test-Connection $DomainName){
        $RetryCounter = 0
        While (Test-FileLock $FilePath) {
            $RetryCounter = $RetryCounter + 1
            Start-Sleep -s $(Get-Random -Maximum 4)
            if (-Not (Test-Connection $DomainName) -or ($RetryCounter -gt 5)) {
                Return $False
                BREAK
            }
        }
        Return $true
    }
    Else {
        Return $False
    }
}

Function ConvertColumn {
    # The function returns the excel columne name of a given number
    Param (
        [int]$ColumnNbr)
    $Remainder = 0
    $Remainder = $ColumnNbr%26
    $ColumnName = $ColumnNbr/26
    $ColumnName = [String]$ColumnName
    $ColumnName = $ColumnName.Substring(0,$ColumnName.IndexOf("."))
    $ColumnName = [Int]$ColumnName

    If ($ColumnName -eq 0) {
        $ColumnNbr = $Remainder
        switch ( $ColumnNbr )
        {
            1 { $first = 'A' }
            2 { $first = 'B' }
            3 { $first = 'C' }
            4 { $first = 'D' }
            5 { $first = 'E' }
            6 { $first = 'F' }
            7 { $first = 'G' }
            8 { $first = 'H' }
            9 { $first = 'I' }
            10 { $first = 'J' }
            11 { $first = 'K' }
            12 { $first = 'L' }
            13 { $first = 'M' }
            14 { $first = 'N' }
            15 { $first = 'O' }
            16 { $first = 'P' }
            17 { $first = 'Q' }
            18 { $first = 'R' }
            19 { $first = 'S' }
            20 { $first = 'T' }
            21 { $first = 'U' }
            22 { $first = 'V' }
            23 { $first = 'W' }
            24 { $first = 'X' }
            25 { $first = 'Y' }
            26 { $first = 'Z' }
        }
    }
    Else {
        $ColumnNbr = $ColumnName
        switch ( $ColumnNbr )
        {
            1 { $first = 'A' }
            2 { $first = 'B' }
            3 { $first = 'C' }
            4 { $first = 'D' }
            5 { $first = 'E' }
            6 { $first = 'F' }
            7 { $first = 'G' }
            8 { $first = 'H' }
            9 { $first = 'I' }
            10 { $first = 'J' }
            11 { $first = 'K' }
            12 { $first = 'L' }
            13 { $first = 'M' }
            14 { $first = 'N' }
            15 { $first = 'O' }
            16 { $first = 'P' }
            17 { $first = 'Q' }
            18 { $first = 'R' }
            19 { $first = 'S' }
            20 { $first = 'T' }
            21 { $first = 'U' }
            22 { $first = 'V' }
            23 { $first = 'W' }
            24 { $first = 'X' }
            25 { $first = 'Y' }
            26 { $first = 'Z' }
        }
        $ColumnNbr = $Remainder
        switch ( $ColumnNbr )
        {
            1 { $Second = 'A' }
            2 { $Second = 'B' }
            3 { $Second = 'C' }
            4 { $Second = 'D' }
            5 { $Second = 'E' }
            6 { $Second = 'F' }
            7 { $Second = 'G' }
            8 { $Second = 'H' }
            9 { $Second = 'I' }
            10 { $Second = 'J' }
            11 { $Second = 'K' }
            12 { $Second = 'L' }
            13 { $Second = 'M' }
            14 { $Second = 'N' }
            15 { $Second = 'O' }
            16 { $Second = 'P' }
            17 { $Second = 'Q' }
            18 { $Second = 'R' }
            19 { $Second = 'S' }
            20 { $Second = 'T' }
            21 { $Second = 'U' }
            22 { $Second = 'V' }
            23 { $Second = 'W' }
            24 { $Second = 'X' }
            25 { $Second = 'Y' }
            26 { $Second = 'Z' }
        }
    }
    Return $first+$Second
}

Function Update_Values {
    # the function updates the values of the cells in the Excel file
    # it is a sub-function of ModifyInventory
    Param (
        [int]$RowNbr
    )
    $Worksheet.Cells.Item($RowNbr,1) = $TimeStamp
    $Worksheet.Cells.Item($RowNbr,2) = $Name
    $Worksheet.Cells.Item($RowNbr,3) = $Model
    $Worksheet.Cells.Item($RowNbr,4) = $User
    $Worksheet.Cells.Item($RowNbr,5) = $OS_Version
    $Worksheet.Cells.Item($RowNbr,6) = $OS_Build
    $Worksheet.Cells.Item($RowNbr,7) = $CPUArchitecture
    $Worksheet.Cells.Item($RowNbr,8) = $Memory
    $Worksheet.Cells.Item($RowNbr,9) = $Harddisks
    $Worksheet.Cells.Item($RowNbr,10) = $BIOS_Version
    $Worksheet.Cells.Item($RowNbr,11) = $BIOS_Date
    $Worksheet.Cells.Item($RowNbr,12) = $SerialNbr
    $Worksheet.Cells.Item($RowNbr,16) = $TeamViewerID
    $Worksheet.Cells.Item($RowNbr,4).Font.ColorIndex = 1
    # Write-Host "Updating Values"
}

Function ModifyInventory {
    # the function write the new inventory data to the Excel file
    [cmdletbinding()]
    Param (
        [parameter(Mandatory)]
        [string]$Source,
        [parameter(Mandatory)]
        [string]$SearchText,
        [string]$ActUser,
        [string]$ComputerName
    )
    # Write-Host "Test-Path o.k"
    $Excel = New-Object -ComObject Excel.Application
    $Excel.Visible = $False
    $Excel.DisplayAlerts = $False
    $ProcessID = ((get-process excel | select MainWindowTitle, ID, StartTime | Sort StartTime)[-1]).Id
    Try {
        $Source = Convert-Path $Source
    }
    Catch {
        BREAK
    }
    # Write-Host "Open Excel o.k."
    $Workbook = $Excel.Workbooks.Open($Source)
    $Worksheet = $Workbook.Worksheets.Item(1)
    $Match = $false

    # Find Method https://msdn.microsoft.com/en-us/vba/excel-vba/articles/range-find-method-excel
    $Found = $WorkSheet.Cells.Find($SearchText) #What
    If ($Found) {
        # UseCase 1-4: Existing Hardware

        $BeginAddress = $Found.Address(0,0,1,1)
        $LastAddress = $Found
        $CName = $Worksheet.Cells.Item($LastAddress.Row,2)
        $UName = $Worksheet.Cells.Item($LastAddress.Row,4)
        $RegUser = $UName.Text

        If ($ComputerName -eq $CName.Text) {
            if ($ActUser -eq $RegUser) {
                # Use Case 1:  No change -> update timestamp and other values
                Update_Values $LastAddress.Row
            }
            ElseIf($RegUser -eq "") {
                # Use Case 4:  existing Hardware has been staged and taken from stock -> update timestamp and other values
                Update_Values $LastAddress.Row
            }
            Else {
                #Use Case 2: Unauthorized computer change -> Save last three computer users
                # Light UserName in red and send eMail
                $Worksheet.Cells.Item($LastAddress.Row,15) = $Worksheet.Cells.Item($LastAddress.Row,14)
                $Worksheet.Cells.Item($LastAddress.Row,14) = $Worksheet.Cells.Item($LastAddress.Row,4)
                $Worksheet.Cells.Item($LastAddress.Row,4).Font.ColorIndex = 3
                Update_Values $LastAddress.Row
                #SendMail $ActUser $RegUser $ComputerName
            }
            $Match = $true
        }

        While ($Match -eq $false)  {
            # Computername different -> Strikethroug existing entry in Excel
            # Light Background yellow and strikethrough the asset
            $Range = $Worksheet.UsedRange
            $Col = ConvertColumn $Range.SpecialCells(11).Column
            $Worksheet.Range("A$($LastAddress.Row)","$Col$($LastAddress.Row)").Interior.ColorIndex = 6
            $Worksheet.Range("A$($LastAddress.Row)","$Col$($LastAddress.Row)").Font.Strikethrough = $True

            # Find next matching SerialNumber
            $Found = $WorkSheet.Cells.FindNext($Found)
            $Address = $Found.Address(0,0,1,1)

            If ($Address -eq $BeginAddress) {
                # UseCase 3: Same Username different ComputerName -> Restage -> Create New Entry
                # or
                # UseCase 4: different Username different ComputerName -> Create New Entry

                $Range = $Worksheet.UsedRange
                $LastRow = $Range.SpecialCells(11).row+1
                Update_Values $LastRow
                $Match = $true
            }
            Else {
                $LastAddress = $Found
                $CName = $Worksheet.Cells.Item($LastAddress.Row,2)
                $UName = $Worksheet.Cells.Item($LastAddress.Row,4)

                if ($ComputerName -eq $CName.Text) {
                    if ($ActUser -ne $RegUser) {
                        # UseCase 2: Different Username same ComputerName -> Change without Staging -> Update User generate Warning
                        # Light Username in red
                        $Worksheet.Cells.Item($LastAddress.Row,15) = $Worksheet.Cells.Item($LastAddress.Row,14)
                        $Worksheet.Cells.Item($LastAddress.Row,14) = $Worksheet.Cells.Item($LastAddress.Row,4)
                        $LastRow = $LastAddress.Row
                        $Worksheet.Cells.Item($LastAddress.Row,4).Font.ColorIndex = 3
                        Update_Values $LastRow
                        #SendMail $ActUser $RegUser $ComputerName
                        $Match = $true
                    }
                    Else {
                        # UseCase 1: Same ComputerName, Same Username -> Update TimeStamp
                        Update_Values $LastRow
                        $Match = $true
                    }
                }
            }
        }
    }
    Else {
        # UseCase 5: New Hardware or Hardware exchange -> Create new entry
        $Range = $Worksheet.UsedRange
        $LastRow = $Range.SpecialCells(11).row+1
        Update_Values $LastRow
    }

    $workbook.Close($true)
    $Excel.Quit()
    [void][System.Runtime.Interopservices.Marshal]::ReleaseComObject($Worksheet)
    [void][System.Runtime.Interopservices.Marshal]::ReleaseComObject($Workbook)
    [void][System.Runtime.Interopservices.Marshal]::ReleaseComObject($Excel)
    [GC]::Collect()
    Stop-Process -Id $ProcessID
}

# ===================================================================================
# Main program starts here
# ===================================================================================
# Get logged on User and check for System Accounts
# ===================================================================================
$UserContext = Whoami
$TokenCJU = "xxyyzz"
$TokenDAS = "xxyyzz"
$User = $UserContext.SubString($UserContext.IndexOf("\")+1,$UserContext.Length-$UserContext.IndexOf("\")-1)
If ($User.ToUpper() -like "SVC_*") { $User = "" }
ElseIf ($User.ToUpper() -eq "ADMINISTRATOR") { $User = "" }
ElseIf ($User.ToUpper() -eq "PUBLIC") { $User = "" }

# ===================================================================================
# Get system information
# ===================================================================================
# exit eingebaut
#$User=""
If ($User -ne "") {
    If ($User.IndexOf(".") -lt 0) {
        $User= $User.ToUpper()
    }
    Else {
        # Extract Username from Login Name
        # e.g. christof.jungo -> Christof Jungo
        $User= $User.Substring(0,1).ToUpper() + $User.Substring(1,$User.IndexOf(".")-1) + " " + $User.Substring($User.IndexOf(".")+1,1).ToUpper() + $User.Substring($User.IndexOf(".")+2,$User.Length-$User.IndexOf(".")-2)
    }
    $DomainName = "argecpc.local"
    $FilePath = "\\CPCNAS004.argecpc.local\CPCNAS\Inventar\ARGECPC_Inventar_Client.xlsx"

    $CPUInfo = Get-WmiObject Win32_Processor -namespace "root\CIMV2"
    $ComputerSystem = Get-WmiObject Win32_ComputerSystem -namespace "root\CIMV2"
    $BIOS = Get-WmiObject Win32_BIOS -namespace "root\CIMV2"

    $TimeStamp = Get-Date -Format dd.MM.yyyy
    $CPUArchitecture = $CPUInfo.Name
    $Name = $ComputerSystem.Name
    $Model = $ComputerSystem.Model
    $MemoryByte = $ComputerSystem.TotalPhysicalMemory
    $Memory = [String] [math]::Round($MemoryByte / 1024 /1024 /1024)
    $Memory = $Memory + " GB"

    $BIOS_Version = $BIOS.SMBIOSBIOSVersion
    #SMBIOSBIOSVersion is special to HP Devices

    $BIOS_Date = $BIOS.ReleaseDate
    $SerialNbr = $BIOS.SerialNumber
    $Harddisks = Get-WmiObject win32_diskdrive | ?{$_.interfacetype -eq "IDE" -or $_.interfacetype -eq "SCSI" }

    $DiskCount = $Harddisks | measure
    $DiskCount = $DiskCount.Count

    ForEach ($Disk in $Harddisks) {
        # Get Disksize in GB or TB e.g. 256 GB
        # if multiple physical disk e.g 256 GB / 2 TB
        $DiskSize =[math]::Round($Disk.Size/1000000000,0)
        $DiskMetric = " GB"
        If ($DiskSize -gt 1000) {
            $DiskSize =[math]::Round($Disk.Size/1000000000000,1)
            $DiskMetric = " TB"
        }
        If ($DiskCount -eq 1) {
            $Harddisks = [String]$DiskSize + $DiskMetric
            $DiskCount = $DiskCount + 1
        }
        Else {
            $Harddisks = $Harddisks +" \ "+ [String]$DiskSize + $DiskMetric
        }
    }
    $TeamViewerID = Get-TeamViewerID $Name


    # ===================================================================================
    # Get Windows 10 Version and Architecture
    # ===================================================================================
    $OS_Architecture = (Get-WmiObject win32_operatingsystem | select osarchitecture).osarchitecture
    $OS_Architecture = $OS_Architecture.ToLower()
    Switch ($OS_Architecture) {
        "64-bit" { $OS_Architecture = "x64" }
        "64 bit" { $OS_Architecture = "x64" }
        "32-bit" { $OS_Architecture = "x32" }
        "32 bit" { $OS_Architecture = "x32" }
        default { $OS_Architecture = "ukn" }
    }

    $OS_Version = switch (((Get-WmiObject Win32_OperatingSystem).name).substring(0,((Get-WmiObject Win32_OperatingSystem).name).IndexOf("|")) ) {
        "Microsoft Windows 10 Pro" { $("Win10"+$OS_Architecture+"Pro") }
        "Microsoft Windows 10 Workstation" { $("Win10"+$OS_Architecture+"Wrk") }
        "Microsoft Windows 10 Enterprise" { $("Win10"+$OS_Architecture+"Ent") }
        default { 'Unknown' }
    }
    # ===================================================================================
    # Get Windows 10 Build (1709 should be standard)
    # ===================================================================================
    $OS_Build = Switch ((Get-WmiObject Win32_OperatingSystem).Version) {
        "10.0.18362" { '1903' }
        "10.0.17763" { '1809' }
        "10.0.17134" { '1803' }
        "10.0.16299" { '1709' }
        "10.0.15063" { '1703' }
        "10.0.14393" { '1607' }
        "10.0.10586" { '1511' }
        "10.0.10240" { 'first' }
        default { 'ukn' }
    }

    # Convert BIOS Date in readable format
    $Year = $BIOS_Date.SubString(0,4)
    $Month = $BIOS_Date.SubString(4,2)
    $Day =$BIOS_Date.SubString(6,2)
    $BIOS_Date=$Day+"."+$Month+"."+$Year

    if (Test-Access $DomainName $FilePath) {
        ModifyInventory $FilePath $SerialNbr $User $Name
        $TeamViewerToken = $TokenCJU
        UpdateTeamViewerDevice $TeamViewerID $Name $User $TeamViewerToken $Model
        $TeamViewerToken = $TokenDAS
        UpdateTeamViewerDevice $TeamViewerID $Name $User $TeamViewerToken $Model
    }
}
