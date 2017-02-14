package none.healthaide.utils;


import android.view.View;
import android.widget.ProgressBar;

public class ProgressBarUtil {
    public static final int MAX_PROGRESS_BAR_VALUE = 100;
    private static final int PROGRESS_BAR_HIDE_DELAY = 200;

    public static void hideProgressBarAfterDelay(final ProgressBar progressBar) {
        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, PROGRESS_BAR_HIDE_DELAY);
    }

    public static void setProgressBarValue(ProgressBar progressBar, int progress) {
        if (progress > MAX_PROGRESS_BAR_VALUE) {
            progress = MAX_PROGRESS_BAR_VALUE;
        } else if (progress < 0) {
            progress = 0;
        }
        progressBar.setProgress(progress);
    }
}
