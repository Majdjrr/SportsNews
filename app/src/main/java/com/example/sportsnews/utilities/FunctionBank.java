/*
 * Copyright (c) 2018
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *   DEVELOPED AND OWNED BY : KHALID RAJAI KADAMANI
 *   EMAIL : KHALIDQADAMANI@GMAIL.COM
 */

package com.example.sportsnews.utilities;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.sportsnews.R;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.M;

/**
 * BASE FUNCTIONS - USED EVERYWHERE IN THE APP
 */
public class FunctionBank {
    private static final FunctionBank ourInstance = new FunctionBank();

    private FunctionBank() {
    }

    public static FunctionBank getInstance() {
        return ourInstance;
    }

    /**
     * Real path from camera
     *
     * @param mContext   -
     * @param contentURI -
     * @return String
     */
    public static String getRealPathFromURIFromCamera(Context mContext, Uri contentURI) {
        if (Build.VERSION.SDK_INT >= M) {
            String path =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mContext.getResources().getString(R.string.app_name) +
                            File.separator + Constants.PICTURE_DIRECTORY + contentURI.getLastPathSegment();
            return path;
        } else {
            Cursor cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file
                return contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                return contentURI.getPath();
            }
        }
    }

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.PICTURE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

//    /**
//     * Function for filling the spinner with required data
//     *
//     * @param spinnerToFill
//     * @param arrayList
//     */
//    public void fillTheSpinner(Context mContext, Spinner spinnerToFill, String[] arrayList) {
//        ArrayAdapter<?> dataAdapter = new ArrayAdapter<>(mContext, R.layout.cell_spinner, R.id.tv_spinner, arrayList);
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(R.layout.cell_spinner);
//        // attaching data adapter to spinner
//        spinnerToFill.setAdapter(dataAdapter);
//    }

//    /**
//     * Function for filling the spinner with required data
//     *
//     * @param spinnerToFill
//     * @param arrayList
//     */
//    public void fillTheSpinner(Context mContext, Spinner spinnerToFill, ArrayList<String> arrayList) {
//        ArrayAdapter<?> dataAdapter = new ArrayAdapter<>(mContext, R.layout.cell_spinner, R.id.tv_spinner, arrayList);
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(R.layout.cell_spinner);
//        // attaching data adapter to spinner
//        spinnerToFill.setAdapter(dataAdapter);
//    }

    /**
     * hide status bar for filling the screen on
     *
     * @param mContext
     */
    public void hideStatusBar(Context mContext) {
        // Ref : https://developer.android.com/training/system-ui/status#java
        View decorView = ((AppCompatActivity) mContext).getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * Check connectivity to the internet
     *
     * @param context
     * @return Connected or Disconnected
     */
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Time Format current time
     *
     * @return string of current time.
     */
    public String getCurrentTime() {
        return new SimpleDateFormat("dd.MM.yyyy - HH.mm", Locale.ENGLISH).
                format(new Timestamp(System.currentTimeMillis()));
    }

//    public String getTimeOnly(String date) {
//        return new SimpleDateFormat("HH.mm", Locale.ENGLISH).format(date);
//    }

    public String getDateOnly(String date) {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(date);
    }

    public void showMsg(Context mContext, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        Log.e("chatActivityTest", msg);
    }

    public void createNotificationChannel(Context mContext, String channelName, String description, String Channel_id) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(Channel_id, channelName, importance);
            channel.setDescription(description);
            channel.setLightColor(mContext.getColor(R.color.colorPrimary));
            channel.enableLights(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // enable location on Android
    public boolean checkLocationEnabled(Context mContext) {
        final LocationManager manager = (LocationManager) (mContext).getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps(mContext);
            return false;
        } else {
            return true;
        }
    }

    private void buildAlertMessageNoGps(Context mContext) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> (mContext).startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public String setPhoneNumber(String phoneNumber) {
        return "962" + (phoneNumber.substring(1));
    }

    /**
     * Function mainly used in Marker resizing map icons
     *
     * @param iconName - name of icon in resources
     * @param width    - specify width
     * @param height   - specify height
     * @return - Bitmap Object
     */
    public Bitmap resizeMapIcons(Context mContext, String iconName, int width, int height) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName())), width, height, false);
    }

    public void startFragment(Context mContext, int layoutFragment, Fragment fragmentClass) {
        FragmentTransaction fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(layoutFragment, fragmentClass).addToBackStack(fragmentClass.getClass().getSimpleName());
        fragmentManager.commit();
        // this is a different commit
    }


    public void startFragmentNullingBackStack(Context mContext, int layoutFragment, Fragment fragmentClass) {
        FragmentTransaction fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(layoutFragment, fragmentClass);
        fragmentManager.commit();
    }

    /**
     * Start Activity with Extra
     *
     * @param mContext     - the mContext that we are in now
     * @param toGoClass    - where to go
     * @param finish       - do we want to finish this class ?
     * @param objectToSend - what to send as seralizable object?
     * @param key          - key to send
     */
    public void startActivity(Context mContext, Class<?> toGoClass,
                              boolean finish, Object objectToSend,
                              String key) {
        Intent intent = new Intent(mContext, toGoClass);
        if (objectToSend != null) {
            intent.putExtra(key, (Serializable) objectToSend);
        }
        mContext.startActivity(intent);
        if (finish) {
            ((AppCompatActivity) mContext).finish();
        }
    }

    /**
     * Start Activity with Extra
     *
     * @param mContext     - the mContext that we are in now
     * @param toGoClass    - where to go
     * @param finish       - do we want to finish this class ?
     * @param objectToSend - what to send as seralizable object?
     */
    public void startActivity(Context mContext, Class<?> toGoClass,
                              boolean finish, Object objectToSend,
                              String key1, int extraInteger, String key2) {
        Intent intent = new Intent(mContext, toGoClass);
        if (objectToSend != null) {
            intent.putExtra(key1, (Serializable) objectToSend);
            intent.putExtra(key2, extraInteger);
        }
        mContext.startActivity(intent);
        if (finish) {
            ((AppCompatActivity) mContext).finish();
        }
    }


//    /**
//     * StartActivity condition with ability to make true or false finished
//     *
//     * @param mContext  -
//     * @param toGoClass -
//     */
//    public void startActivity(Context mContext, Class<?> toGoClass, PAGES_TO_MOVE pages_to_move) {
//        Intent intent = new Intent(mContext, toGoClass);
//        intent.putExtra("PageToMove", pages_to_move);
//        mContext.startActivity(intent);
//
//    }

    /**
     * Basic method for logind images using Glide
     *
     * @param url       - the url
     * @param imageView - the image to load into
     */

//    public void glideFramgentImage(String url, ImageView imageView, Fragment myFragment) {
//        GlideApp
//                .with(myFragment)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .into(imageView);
//    }
//
//    public void glideFramgentImage(String url, ImageView imageView, AppCompatActivity myActivity) {
//        GlideApp
//                .with(myActivity)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .into(imageView);
//    }
//
//    public void glideFramgentImage(String url, com.github.abdularis.civ.CircleImageView imageView, AppCompatActivity myActivity) {
//        GlideApp
//                .with(myActivity)
//                .load(url)
//                .skipMemoryCache(false)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .into(imageView);
//    }
//
//    public void glideFramgentImage(String url, com.github.abdularis.civ.CircleImageView imageView, Fragment myActivity) {
//        GlideApp
//                .with(myActivity)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .into(imageView);
//    }

    /**
     * Snackbar Toast implementation
     *
     * @param mContext - mContext to be applied
     * @param view     - which view should Snackbar have
     * @param message  - message to appear
     */
//    public void showSnackbar(final Context mContext, View view, final String message) {
//        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
//        snackbar.setAction(mContext.getString(R.string.okText),
//                view1 -> snackbar.dismiss());
//        snackbar.show();
//    }

    /**
     * Hide virtual key on mContext
     *
     * @param mContext - mContext
     */
    public void hideSoftKey(Context mContext) {
        ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


//    /**
//     * Useful - optionsl
//     */
//    public String calculateDuration(String dateOfPosting) {
//        String timeToReturn = "";
//        //https://stackoverflow.com/questions/44207372/calcuting-the-date-difference-for-a-specified-number-of-days-using-localdate-cla/44208828
//        try {
//
//            Calendar currentDate = Calendar.getInstance();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
//            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//            String timeNow = currentDate.get(Calendar.YEAR) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.DAY_OF_MONTH) + " " +
//                    currentDate.get(Calendar.HOUR_OF_DAY) + ":" + currentDate.get(Calendar.MINUTE) + ":" + currentDate.get(Calendar.SECOND);
//            long diff = dateFormat.parse(timeNow).getTime() - dateFormat.parse(dateOfPosting).getTime();
//            if (TimeUnit.MILLISECONDS.toDays(diff) >= 1) {
//                timeToReturn = timeToReturn + TimeUnit.MILLISECONDS.toDays(diff) + " Days Ago";
//            } else if (TimeUnit.MILLISECONDS.toHours(diff) >= 1) {
//                timeToReturn = timeToReturn + TimeUnit.MILLISECONDS.toHours(diff) + " Hours Ago";
//            } else if (TimeUnit.MILLISECONDS.toMinutes(diff) >= 1) {
//                timeToReturn = timeToReturn + TimeUnit.MILLISECONDS.toMinutes(diff) + " Minutes Ago";
//            } else if (TimeUnit.MILLISECONDS.toSeconds(diff) >= 1) {
//                timeToReturn = timeToReturn + TimeUnit.MILLISECONDS.toSeconds(diff) + " Seconds Ago";
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return "";
//        }
//        return timeToReturn;
//    }

    public void showBrowser(Context mContext, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (url.contains("http://")) {
            i.setData(Uri.parse(url));
        } else {
            i.setData(Uri.parse("http://" + url));
        }
        mContext.startActivity(i);
    }

    /**
     * Logs
     * Log.e(String, String) (error)
     * Log.w(String, String) (warning)
     * Log.i(String, String) (information)
     * Log.d(String, String) (debug)
     * Log.v(String, String) (verbose)
     *
     * @return void of logging
     */
//    public void log(LOGS_ENUM logs_enum, String contextFrom, String message) {
//        switch (logs_enum) {
//            case D:
//                Log.d(contextFrom, message);
//                break;
//            case W:
//                Log.w(contextFrom, message);
//                break;
//            case I:
//                Log.i(contextFrom, message);
//                break;
//            case V:
//                Log.v(contextFrom, message);
//                break;
//            case E:
//                Log.e(contextFrom, message);
//                break;
//        }
//    }

    /**
     * starting new fragment operation
     *
     * @param mContext - context to use
     * @param fragment - fragment
     */
//    public void startFragment(Context mContext, Fragment fragment) {
//        FragmentManager mFragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
//        androidx.fragment.app.FragmentTransaction mFragmentTransaction =
//                mFragmentManager.beginTransaction().addToBackStack(fragment.getClass().getSimpleName());
//        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        mFragmentTransaction.replace(R.id.fragment_container, fragment).commit();
//    }

    /**
     * show message of toast
     *
     * @param mContext - because of context container
     * @param message  - string show
     */
    public void msg(Context mContext, String message) {
        Log.d("eee",message);

    }

    public void startActivity(Context mContexxt, Class classToGo, boolean isFinished) {
        Intent intent = new Intent(mContexxt, classToGo);
        if (isFinished) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        mContexxt.startActivity(intent);
    }


    /**
     * Show Toast Message
     *
     * @param mContext
     * @param title
     */
    public void getMessageToast(Context mContext, String title) {
        Toast.makeText(mContext, title, Toast.LENGTH_LONG).show();
    }

    /**
     * function to show image on to the image holder
     *
     * @param mContext  -
     * @param url       - to show
     * @param imageView -
     */
    public void showImage(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext).load(url).
                placeholder(R.mipmap.ic_launcher).
                into(imageView);
    }


    /**
     //     * Save Facebook Token
     //     *
     //     * @param mContext - to get from
     //     */
//    public void saveFacebookToken(Context mContext, String token) {
//        SharedPreferences.Editor editor = mContext.getSharedPreferences(AppConstant.FACEBOOK_TOKEN, MODE_PRIVATE).edit();
//        editor.putString(AppConstant.FACEBOOK_TOKEN, token);
//        editor.apply();
//    }

//    /**
//     * Save ObjUser Object
//     *
//     * @param mContext - to get from
//     * @param ObjUser     - Object
//     */
//    public void saveUser(Context mContext, ObjUser ObjUser) {
//        SharedPreferences.Editor editor = mContext.getSharedPreferences(AppConstant.USER_OBJECT, MODE_PRIVATE).edit();
//        if (getPassword(mContext) != null) {
//            editor.clear().commit();
//        }
//        editor.putString(AppConstant.USER_OBJECT, new Gson().toJson(ObjUser, ObjUser.class));
//        editor.apply();
//    }


//    /**
//     * Get Saved ObjUser
//     *
//     * @param mContext - to which mContext is
//     * @return ObjUser Object
//     */
//    public ObjUser getPassword(Context mContext) {
//        SharedPreferences prefs = mContext.getSharedPreferences(AppConstant.USER_OBJECT, MODE_PRIVATE);
//        return new Gson().fromJson(prefs.getString(AppConstant.USER_OBJECT, null), ObjUser.class);
//    }

//    /**
//     * Calculating difference between 2 points
//     *
//     * @param firstLat  - lat 1
//     * @param firstLon  - lon 1
//     * @param secondLat - lat 2
//     * @param secondLon - lon 2
//     * @return double distance
//     */
//    public double calculationByDistance(double firstLat, double firstLon, double secondLat, double secondLon) {
//        if (new LatLng(firstLat, firstLon) == null || new LatLng(secondLat, secondLon) == null) {
//            return 0.0;
//        }
//        return (SphericalUtil.computeDistanceBetween(new LatLng(firstLat, firstLon), new LatLng(secondLat, secondLon)) / 1000);
//    }
//
//    public void showErrorMessage(Context mContext, View layout, String errorMessage) {
//        FunctionBank.getInstance().showSnackbar(mContext, layout, errorMessage);
//    }

}