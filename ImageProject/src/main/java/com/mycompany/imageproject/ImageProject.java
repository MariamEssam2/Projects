package com.mycompany.imageproject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ImageProject {
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of image file: ");
        String filePath = scanner.nextLine();
        scanner.close();
        try {
            String fileType = File(filePath);
            System.out.println("This file is " + fileType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String File(String filePath) throws IOException {
        FileInputStream i = null;
        try {
            i = new FileInputStream(filePath);

            byte[] header = new byte[8];
            int bytesRead = i.read(header, 0, 8);

            if (bytesRead >= 4) {
                if (isJPEG(header)) {
                    return "JPEG";
                }
                if (isTIFF(header)) {
                    return "TIFF";
                }
                if (isRAW(header)) {
                    return "RAW";
                }
                if (isPNG(header)) {
                    return "PNG";
                }
                if (isGIF(header)) {
                    return "GIF";
                }
                if (isBMP(header)) {
                    return "BMP";
                }

                return "not supported";
            }
        } finally {
            if (i != null) {
                try {
                    i.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "unsupported";
    }

    private static boolean isJPEG(byte[] header) {
        return header[0] == (byte) 0xFF && header[1] == (byte) 0xD8;
    }

    private static boolean isTIFF(byte[] header) {
        return header[0] == (byte) 0x49 && header[1] == (byte) 0x49 && header[2] == (byte) 0x2A && header[3] == (byte) 0x00;
    }

    private static boolean isRAW(byte[] header) {
        return header[0] == (byte) 0x52 && header[1] == (byte) 0x49 && header[2] == (byte) 0x46 && header[3] == (byte) 0x46;
    }

    private static boolean isPNG(byte[] header) {
        return header[0] == (byte) 0x89 && header[1] == (byte) 0x50 && header[2] == (byte) 0x4E && header[3] == (byte) 0x47;
    }

    private static boolean isGIF(byte[] header) {
        return header[0] == (byte) 0x47 && header[1] == (byte) 0x49 && header[2] == (byte) 0x46 && header[3] == (byte) 0x38;
    }

    private static boolean isBMP(byte[] header) {
        return header[0] == (byte) 0x42 && header[1] == (byte) 0x4D;
}
}
