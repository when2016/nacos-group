package service.utils;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;

public class TestUtil {

    //http://static.cdn.longmaosoft.com/103501/887284/AtIVEuM9Ap8X2W7r8rsrEVbXf4Ui6n6I.silk

    public static void converterAudio(URL url, OutputStream ops) {

        File fSilk = null;
        File fWav = null;
        try {
            try {
                String wavFilePath = "E:\\Downloads\\AtIVEuM9Ap8X2W7r8rsrEVbXf4Ui6n6I.wav";
                System.out.println("wavFilePath==" + wavFilePath);
                fWav = new File(wavFilePath);
                FileUtils.copyFile(fWav, ops);
            } catch (Exception e) {
                System.out.println("e-->" + e);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
