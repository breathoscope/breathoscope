package org.inspire.breath;
import java.io.*;

public class WawReader {



    public static int getResult(File res){
        int counter = 0;
        try
        {
            // Open the wav file specified as the first argument
            WavFile wavFile = WavFile.openWavFile(res);

            // Display information about the wav file
            wavFile.display();

            // Get the number of audio channels in the wav file
            int numChannels = wavFile.getNumChannels();

            // Create a buffer of 100 frames
            double[] buffer = new double[80 * numChannels];

            int framesRead;
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            do
            {
                // Read frames into buffer
                framesRead = wavFile.readFrames(buffer, 80);

                // Loop through frames and look for minimum and maximum value
                for (int s=0 ; s<framesRead * numChannels ; s++)
                {
                    if (buffer[s] > max) max = buffer[s];
                    if (buffer[s] < min) min = buffer[s];
                }
            }
            while (framesRead != 0);

            // Close the wavFile
            wavFile.close();

            // Output the minimum and maximum value
            System.out.printf("Min: %f, Max: %f\n", min, max);

            for(int i =0; i<buffer.length-2;i++) {
                System.out.printf("%f\n", buffer[i]);
                if((buffer[i+1]-buffer[i])*(buffer[i+2]-buffer[i+1]) <= 0) {
                    counter++;
                }

            }
           // System.out.println(counter/2);
        }
        catch (Exception e)
        {
            System.err.println(e);
        }


        return counter/2;
    }

}
