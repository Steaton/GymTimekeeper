package one.thing.well.gymtimekeeper;

import android.app.Application;
import android.content.Context;

public class GymTimekeeperApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        GymTimekeeperApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return GymTimekeeperApplication.context;
    }
}
