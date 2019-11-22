package org.bfh.dms.utils;

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class ScriptUtils {

    public String convertName(String name) {
        StringBuilder nameBuilder = new StringBuilder();
        String uppercaseFirstNameLetter = name.substring(0, 1).toUpperCase();
        String restOfFirstName = name.substring(1, name.indexOf('.'));
        String uppercaseLastNameLetter = name.substring(name.indexOf('.') + 1, name.indexOf('.') + 2).toUpperCase();
        String restOfLastName = name.substring(name.indexOf('.') + 2);
        nameBuilder.append(uppercaseFirstNameLetter);
        nameBuilder.append(restOfFirstName);
        nameBuilder.append(" ");
        nameBuilder.append(uppercaseLastNameLetter);
        nameBuilder.append(restOfLastName);
        System.out.println(nameBuilder.toString());
        return nameBuilder.toString();
    }

    public String convertMemory(String memoryInBytes) {
        Double memoryInBytesDouble = Double.parseDouble(memoryInBytes);
        Double memoryInGigs = memoryInBytesDouble / 1024 / 1024 / 1024;
        long memoryInGigsRounded = Math.round(memoryInGigs);
        System.out.println(memoryInGigsRounded + " GB");
        return memoryInGigsRounded + " GB";
    }

    public String convertHardiskSizes(String hardDisks) {
        List<String> hardDiskSizes = Arrays.asList(hardDisks.split("::"));
        StringBuilder stringBuilder = new StringBuilder();
        String separator = "";
        for (String hardDiskSize : hardDiskSizes) {
            String metric = " GB";
            String trimmedHarddiskSize = hardDiskSize.trim();
            long size = Long.parseLong(trimmedHarddiskSize) / 1000000000;
            if (size >= 1000) {
                metric = " TB";
                size = size / 1000;
            }
            stringBuilder.append(separator);
            stringBuilder.append(size);
            stringBuilder.append(metric);
            separator = " \\ ";
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String convertOSName(String os) {
        List<String> osInfos = Arrays.asList(os.split("::"));
        if (osInfos.size() == 2) {
            String version = osInfos.get(0).substring(0, osInfos.get(0).indexOf('|'));
            String architecture = osInfos.get(1);
            System.out.println(getFormattedOsString(version, architecture));
            return getFormattedOsString(version, architecture);
        }
        return "unknown";
    }

    public String getFormattedOsString(String version, String architecture) {
        switch (version) {
            case "Microsoft Windows 10 Pro":
                return "Win10" + getFormattedArchitectureString(architecture) + "Pro";
            case "Microsoft Windows 10 Workstation":
                return "Win10" + getFormattedArchitectureString(architecture) + "Wrk";
            case "Microsoft Windows 10 Enterprise":
                return "Win10" + getFormattedArchitectureString(architecture) + "Ent";
            default:
                return "unknown";
        }
    }

    public String getFormattedArchitectureString(String architecture) {
        String lowercaseArchitecture = architecture.toLowerCase();
        switch (lowercaseArchitecture) {
            case "64-bit":
                return "x64";
            case "64 bit":
                return "x64";
            case "32-bit":
                return "x32";
            case "32 bit":
                return "x32";
            default:
                return "unknown";
        }
    }

    public String convertOsBuild(String osBuild) {
        switch (osBuild) {
            case "10.0.18362":
                return "1903";
            case "10.0.17763":
                return "1809";
            case "10.0.17134":
                return "1803";
            case "10.0.16299":
                return "1709";
            case "10.0.15063":
                return "1703";
            case "10.0.14393":
                return "1607";
            case "10.0.10586":
                return "1511";
            case "10.0.10240":
                return "first";
            default:
                return "unknown";
        }
    }

    public String convertBiosDate(String biosDate){
        return biosDate.substring(0, 4) + "-" + biosDate.substring(4,6) + "-" + biosDate.substring(6,8);
    }
}
