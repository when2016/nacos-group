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

public class SilkToWavUtil {

    //http://static.cdn.longmaosoft.com/103501/887284/AtIVEuM9Ap8X2W7r8rsrEVbXf4Ui6n6I.silk

    private static final String shellPath = "/srv/silk2wav/silk-v3-decoder/converter.sh";

    public static void converterAudio(URL url, OutputStream ops) {

        File fSilk = null;
        File fWav = null;
        try {
            fSilk = File.createTempFile("audio-convert", ".silk");
            FileUtils.copyURLToFile(url, fSilk);

            String fSilkPath = fSilk.getAbsolutePath();
            String command = String.format("sh %s %s wav", shellPath, fSilkPath);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            CommandLine commandline = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();

            ExecuteWatchdog watchdog = new ExecuteWatchdog(10 * 60 * 1000);
            exec.setWatchdog(watchdog);
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
            exec.setStreamHandler(streamHandler);
            int waitFor = exec.execute(commandline);
            System.out.println("waitFor==" + waitFor);
            if (waitFor == 0) {
                try {
                    String wavFilePath = fSilkPath.replace(".silk", ".wav");
                    fWav = new File(wavFilePath);
                    FileUtils.copyFile(fWav, ops);
                } catch (Exception e) {
                    System.out.println("e-->" + e);
                }
            } else {
                System.out.println("执行失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.deleteQuietly(fSilk);
            FileUtils.deleteQuietly(fWav);
        }
    }

}
