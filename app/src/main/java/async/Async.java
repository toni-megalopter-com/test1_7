package async;

import android.os.AsyncTask;


public class Async extends AsyncTask<Void, Void, Void> {
    private MyRun doit;
    private MyRun endit;
    public Async(MyRun Doit, MyRun Endit) {
        doit = Doit;
        endit = Endit;
    }

    @Override
    protected Void doInBackground(Void... params) {
        doit.run();
        return null;
    }

    @Override
    protected void onPostExecute(Void param) {
        endit.run();
    }
}

