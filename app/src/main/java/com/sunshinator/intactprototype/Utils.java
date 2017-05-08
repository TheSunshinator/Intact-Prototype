package com.sunshinator.intactprototype;

import android.content.Context;
import android.os.Build;
import java.util.Locale;

/**
 * Library of random methods
 * <p>
 * Created by The Sunshinator on 06/05/2017.
 */
public class Utils {

  /**
   * @param context Whatever context to access resources
   * @return Locale depending on the Android version of the device and its settings
   */
  public static Locale getLocale( Context context ) {

    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
      return context.getResources().getConfiguration().getLocales().get( 0 );
    } else {
      //noinspection deprecation
      return context.getResources().getConfiguration().locale;
    }
  }
}
