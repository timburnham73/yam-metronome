package ch.reevolt.metronome.graphic;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

;

/**
 * 
 * @author Romain Cherix
 * 
 *         this class allows to use ImageView like a Button.
 * 
 */
public class ButtonImageView extends ImageView implements OnClickListener,
		OnTouchListener {

	private Timer timer;
	private TimerTask timerTask;
	Handler buttonHandler;
	public int aplha;
	boolean isRunning = false;
	boolean enable = true;
	boolean visible = true;

	OnClickedListener listener;

	/**
	 * @param context
	 */
	public ButtonImageView(Context context) {
		super(context);
		initialize();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ButtonImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ButtonImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	/**
	 * initialize the button
	 */
	public void initialize() {

		buttonHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ButtonImageView.this.setAlpha((int) (msg.arg1 * 1.59) + 96);
			}
		};

		this.setOnClickListener(this);
		this.setOnTouchListener(this);

		timer = new Timer();

	}

	/**
	 * Action on click
	 */
	public void onClick(View v) {

		if (enable) {
			timerTask = new TimerTask() {
				byte value = (byte) 0;

				@Override
				public void run() {
					Message msg = new Message();
					msg.arg1 = value++;
					buttonHandler.sendMessage(msg);
					if (value == 100)
						this.cancel();
				}
			};
			timer.scheduleAtFixedRate(timerTask, 0, 2);

			listener.onButtonClick(v);
		}

	}

	/**
	 * @param enable
	 *          state of the button
	 */
	public void setEnable(boolean enable) {
		if (!enable) {
			this.setAlpha(96);
			this.enable = false;
		} else {
			this.setAlpha(255);
			this.enable = true;
		}
	}

	/**
	 * @return the visibility of the button
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *          the visibility of the button
	 */
	public void setVisible(boolean visible) {
		if (!visible) {
			this.setAlpha(96);
			this.visible = false;
		} else {
			this.setAlpha(255);
			this.visible = true;
		}
	}

	/**
	 * Interface with listener
	 * 
	 * @param listener
	 *          listener to callback
	 */
	public void setOnClickedListener(OnClickedListener listener) {
		this.listener = listener;
	}

	public interface OnClickedListener {
		public void onButtonClick(View v);
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			actionOnPress();
			break;
		case MotionEvent.ACTION_UP:
			actionOnRelease();
			break;
		}

		return false;
	}

	/**
	 * method to override in order to modify behavior on action performed
	 */
	public void actionOnPress() {

	}

	/**
	 * method to override in order to modify behavior on action performed
	 */
	public void actionOnRelease() {

	}

}
