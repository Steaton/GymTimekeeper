package one.thing.well.gymtimekeeper.persist;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class InTheGymFileReader {

    private Context context;

    public InTheGymFileReader(Context context) {
        this.context = context;
    }

    public InTheGymLocationEventFile readGymLocationEventFile() {

        try {
            return readGymLocationEventFile("InTheGymLocationEvents");
        } catch (IOException e) {
            e.printStackTrace();
            return new InTheGymLocationEventFile();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new InTheGymLocationEventFile();
        }
    }

    private InTheGymLocationEventFile readGymLocationEventFile(String filename) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(filename);
        return readFile(bufferedReader);
    }

    @NonNull
    private InTheGymLocationEventFile readFile(BufferedReader bufferedReader) throws IOException {
        InTheGymLocationEventFile inTheGymLocationEventFile = new InTheGymLocationEventFile();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            inTheGymLocationEventFile.addEvent(line);
        }
        return inTheGymLocationEventFile;
    }

    @NonNull
    private BufferedReader getBufferedReader(String filename) throws FileNotFoundException {
        FileInputStream fileInputStream = context.getApplicationContext().openFileInput(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        return new BufferedReader(inputStreamReader);
    }
}
