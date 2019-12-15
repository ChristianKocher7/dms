$UserContext = whoami
$User = $UserContext.SubString($UserContext.IndexOf("\")+1,$UserContext.Length-$UserContext.IndexOf("\")-1)
If ($User.ToUpper() -like "SVC_*") { $User = "" }
ElseIf ($User.ToUpper() -eq "ADMINISTRATOR") { $User = "" }
ElseIf ($User.ToUpper() -eq "PUBLIC") { $User = "" }

#check if the server is present
If ($User -ne "") {
    If ($User.IndexOf(".") -lt 0) {
        $User= $User.ToUpper()
    }

    #TODO (for test purposes only) remove for prod
    $User = "christof.jungo"

    $CPUInfo = Get-WmiObject Win32_Processor -namespace "root\CIMV2"
    $ComputerSystem = Get-WmiObject Win32_ComputerSystem -namespace "root\CIMV2"
    $BIOS = Get-WmiObject Win32_BIOS -namespace "root\CIMV2"

    $TimeStamp = Get-Date -Format yyyy-MM-dd
    $CPUArchitecture = $CPUInfo.Name
    $Name = $ComputerSystem.Name
    $Model = $ComputerSystem.Model
    $MemoryByte = $ComputerSystem.TotalPhysicalMemory

    $BIOS_Version = $BIOS.SMBIOSBIOSVersion
    #SMBIOSBIOSVersion is special to HP Devices

    $BIOS_Date = $BIOS.ReleaseDate
    $SerialNbr = $BIOS.SerialNumber
    $Harddisks = Get-WmiObject win32_diskdrive | ?{$_.interfacetype -eq "IDE" -or $_.interfacetype -eq "SCSI" }

    $HarddiskInfo = " "
    $Separator = " "

    ForEach ($Disk in $Harddisks) {
        $HarddiskInfo = $HarddiskInfo + $Separator + $Disk.Size
        $Separator = "::"
    }

    # ===================================================================================
    # Get Windows 10 Version and Architecture
    # ===================================================================================
    $OS_Architecture = (Get-WmiObject win32_operatingsystem | select osarchitecture).osarchitecture

    $OS_Version = (Get-WmiObject Win32_OperatingSystem).name + "::" +$OS_Architecture

    # ===================================================================================
    # Get Windows 10 Build (1709 should be standard)
    # ===================================================================================
    $OS_Build = (Get-WmiObject Win32_OperatingSystem).Version

    $JSON = @{
        timestamp = $TimeStamp
        deviceName = $Name
        model = $Model
        deviceUser = $User
        os = $OS_Version
        build = $OS_Build
        cpu = $CPUArchitecture
        memory = $MemoryByte
        hardDisk = $HarddiskInfo
        installedBiosVersion = $BIOS_Version
        biosDate = $BIOS_Date
        serialNumber = $SerialNbr
        maintenance = "2019-11-22"
        previousUser1 = "Vincent Van Gogh"
        previousUser2 = "Achilles"
        teamviewerId = "123546"
    } | ConvertTo-Json

    #basic error handling
    Invoke-WebRequest -Uri http://localhost:8080/script/device -Method POST -Body $JSON -ContentType "application/json" -Headers @{'Authorization' = 'Basic YWRtaW46MTIzNA=='}
}
