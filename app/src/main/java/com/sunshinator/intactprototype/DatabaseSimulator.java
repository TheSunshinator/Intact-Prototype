package com.sunshinator.intactprototype;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Fetches the catalog from a file and simulate interactions with a database
 * <p>
 * Created by The Sunshinator on 06/05/2017.
 */
public class DatabaseSimulator {

  private static final String LOG_TAG = DatabaseSimulator.class.getSimpleName();

  private static final List<CatalogItem> ITEMS    = new ArrayList<>();
  private static final Set<CatalogItem>  WISHLIST = new HashSet<>();

  /**
   * Initialises the database simulation by loading the data from
   * database.json
   *
   * @param context Whatever context to access resources
   */
  public static void init( Context context ) {

    try {

      JSONObject data    = new JSONObject( readDatabaseFile( context ) );
      JSONArray  objects = data.getJSONArray( "data" );

      for ( int i = 0; i < objects.length(); i++ ) {
        JSONObject instance = objects.getJSONObject( i );

        ITEMS.add( parse( context, instance ) );
      }

    } catch ( IOException | JSONException e ) {
      Log.e( LOG_TAG, "init: ", e );
    }

  }

  /**
   * @param context Whatever context to access resources
   * @param instance JSONObject to parse
   * @return CatalogItem filed with the data in (@param instance)
   * @throws JSONException if (@param instance) is not formatted properly
   */
  private static CatalogItem parse( Context context, JSONObject instance )
      throws JSONException {

    CatalogItem item = new CatalogItem();
    item.setUid( instance.getString( "uid" ) );
    item.setName( instance.getString( "name" ) );
    item.setDescriptionShort( instance.getString( "short" ) );
    item.setDescriptionLong( instance.getString( "long" ) );
    item.setSize( instance.getString( "size" ) );
    item.setPrice( instance.getInt( "price" ) );

    JSONArray colors     = instance.getJSONArray( "colors" );
    int[]     colorCodes = new int[colors.length()];
    for ( int j = 0; j < colorCodes.length; j++ ) {
      colorCodes[j] = colors.getInt( j );
    }
    item.setColors( colorCodes );

    String filename = instance.getString( "thumbnail" );
    int thumbnailId = context.getResources().getIdentifier( filename, "drawable",
                                                            context.getPackageName() );
    item.setThumbnail( thumbnailId );

    return item;
  }

  /**
   * @param context Whatever context to access resources
   *
   * @return String representation of the JSON in database.json
   *
   * @throws IOException if file reading fails
   */
  @NonNull
  private static String readDatabaseFile( Context context )
      throws IOException {

    InputStream    is     = context.getAssets().open( "database.json" );
    BufferedReader reader = new BufferedReader( new InputStreamReader( is ) );
    StringBuilder  out    = new StringBuilder();
    String         line;

    while ( ( line = reader.readLine() ) != null ) {
      out.append( line );
    }
    reader.close();
    is.close();

    return out.toString();
  }

  /**
   * Removes {@param item} from wish list if it already is in the wish list.
   * Adds {@param item} to wish list if it's not already in the wish list.
   * @param item Item to add/remove from wish list
   */
  public static void triggerWishList( CatalogItem item ) {

    if ( !WISHLIST.remove( item ) ) { WISHLIST.add( item ); }
  }

  /**
   * @return A list of all CatalogItem in the wish list
   */
  public static List<CatalogItem> getWishList() {

    return new ArrayList<>( WISHLIST );
  }

  /**
   * @return A list of all CatalogItem in the wish list
   */
  public static List<CatalogItem> getAllItems() {

    return new ArrayList<>( ITEMS );
  }

  /**
   * @param uid UID of the CatalogItem to return
   * @return The CatalogItem with the corresponding UID
   */
  @Nullable
  public static CatalogItem getItem( String uid ) {

    for ( CatalogItem item : ITEMS ) {
      // .equals() is overridden in CatalogItem and supports .equals(String)
      //noinspection EqualsBetweenInconvertibleTypes
      if ( item.equals( uid ) ) {
        return item;
      }
    }

    return null;
  }
}
