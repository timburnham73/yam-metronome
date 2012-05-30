package ch.reevolt.metronome.graphic;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import ch.reevolt.metronome.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import kankan.wheel.widget.adapters.AbstractWheelAdapter;

/**
 * Slot machine adapter
 */
public class ImageViewAdapater extends AbstractWheelAdapter {
	// Image size
	final int IMAGE_WIDTH = 80;
	final int IMAGE_HEIGHT = 220;

	// Slot machine symbols
	private final int items[] = new int[] { R.drawable.w24, R.drawable.w34,
			R.drawable.w44, R.drawable.w38, R.drawable.w68, R.drawable.w98,
			R.drawable.w128 };

	// Cached images
	private List<SoftReference<Bitmap>> images;

	// Layout inflater
	private Context context;

	/**
	 * Constructor
	 */
	public ImageViewAdapater(Context context) {

		this.context = context;
		images = new ArrayList<SoftReference<Bitmap>>(items.length);
		for (int id : items) {
			images.add(new SoftReference<Bitmap>(loadImage(id)));
		}
	}

	/**
	 * Loads image from resources
	 */
	private Bitmap loadImage(int id) {
		Bitmap bitmap = BitmapFactory
				.decodeResource(context.getResources(), id);
		Bitmap scaled = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH,
				IMAGE_HEIGHT, true);
		bitmap.recycle();
		return scaled;
	}

	public int getItemsCount() {
		return items.length;
	}

	// Layout params for image view
	final LayoutParams params = new LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);

	public View getItem(int index, View cachedView, ViewGroup parent) {
		ImageView img;
		if (cachedView != null) {
			img = (ImageView) cachedView;
		} else {
			img = new ImageView(context);
		}
		img.setLayoutParams(params);
		SoftReference<Bitmap> bitmapRef = images.get(index);
		Bitmap bitmap = bitmapRef.get();
		if (bitmap == null) {
			bitmap = loadImage(items[index]);
			images.set(index, new SoftReference<Bitmap>(bitmap));
		}
		img.setImageBitmap(bitmap);

		return img;
	}

}