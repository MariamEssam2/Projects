
package com.mycompany.soundproject2;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class SoundProject2 {
    public static void main(String[] args) {
        String filePath;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the path of audio file: ");
            filePath = scanner.nextLine();
        }
        
        File audioFile = new File(filePath);
        
        try {
            FileInputStream f = new FileInputStream(audioFile);
            
            byte[] header = new byte[4];
            int bytesRead = f.read(header);
            
            if (bytesRead >= 4) {
                String fileFormat = new String(header);
                
                if (fileFormat.equals("RIFF")) {
                    System.out.println("The input file is a WAV file.");
                    
                   
                    byte[] formatChunk = new byte[36];
                    bytesRead = f.read(formatChunk);
                    
                    if (bytesRead >= 36) {
                        int formatChunkSize = byteArrayToInt(formatChunk, 16, 4);
                        int formatTag = byteArrayToInt(formatChunk, 20, 2);
                        int numChannels = byteArrayToInt(formatChunk, 22, 2);
                        int samplesPerSec = byteArrayToInt(formatChunk, 24, 4);
                        int avgBytesPerSec = byteArrayToInt(formatChunk, 28, 4);
                        int blockAlign = byteArrayToInt(formatChunk, 32, 2);
                        int bitsPerSample = byteArrayToInt(formatChunk, 34, 2);
            
                        System.out.println("Format Chunk Size: " + formatChunkSize);
                        System.out.println("Format Tag: " + formatTag);
                        System.out.println("Number of Channels: " + numChannels);
                        System.out.println("Samples Per Second: " + samplesPerSec);
                        System.out.println("Average Bytes Per Second: " + avgBytesPerSec);
                        System.out.println("Block Align: " + blockAlign);
                        System.out.println("Bits Per Sample: " + bitsPerSample);
                    } else {
                        System.out.println("error");
                    }
                  
                    byte[] dataChunkHeader = new byte[8];
                    bytesRead = f.read(dataChunkHeader);

                    if (bytesRead >= 8) {
                        String dataChunkId = new String(dataChunkHeader, 0, 4);
                        int dataChunkSize = byteArrayToInt(dataChunkHeader, 4, 4);

                        System.out.println("Data Chunk ID: " + dataChunkId);
                        System.out.println("Data Chunk Size: " + dataChunkSize);
                    } else {
                        System.out.println("Error.");
                    }

                } else if (fileFormat.equals("ID3")) {
                    System.out.println("The input file is an MP3 file.");
                    
                    byte[] headerBytes = new byte[10];
                    bytesRead = f.read(headerBytes);
                    
                    if (bytesRead >= 10) {
                        int version = (headerBytes[3] >> 3) & 0x03;
                        int layer = (headerBytes[3] >> 1) & 0x03;
                        int bitRateIndex = (headerBytes[2] >> 4) & 0x0F;
                        int sampleRateIndex = (headerBytes[2] >> 2) & 0x03;
                        int channelMode = headerBytes[3] & 0x03;
            
                        int[] bitRates = { 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 0 };
                        int[] sampleRates = { 44100, 48000, 32000, 0 };
                        String[] channelModes = { "Stereo", "Joint Stereo", "Dual Channel", "Mono" };
            
                        int bitRate = bitRates[bitRateIndex];
                        int sampleRate = sampleRates[sampleRateIndex];
                        String channelModeString = channelModes[channelMode];
            
                        System.out.println("Version MPEG" + version);
                        System.out.println("Layer " + layer);
                        System.out.println("Bit Rate " + bitRate + " kbps");
                        System.out.println("Sample Rate " + sampleRate + " Hz");
                        System.out.println("Channel Mode " + channelModeString);
                    } else {
                        System.out.println("error");
                    }
                } else {
                    System.out.println("The input file format is not supported.");
                }
            } else {
                System.out.println("error");
            }
            
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static int byteArrayToInt(byte[] byteArray, int offset, int length) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            result |= (byteArray[offset + i] & 0xFF) << (8 * i);
        }
        return result;
    }
}