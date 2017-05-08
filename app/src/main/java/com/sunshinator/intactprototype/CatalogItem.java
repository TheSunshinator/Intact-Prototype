package com.sunshinator.intactprototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Object representation of items in the catalog
 *
 * Created by The Sunshinator on 06/05/2017.
 */
public class CatalogItem {

  private int miPrice = 0;
  private int miThumbnail;

  private String msName = null;
  private String msUid = null;
  private String msDescriptionLong = null;
  private String msDescriptionShort = null;
  private String msSize = null;

  private int[] mColors = null;

  @Override
  public int hashCode() {

    return msUid.hashCode();
  }

  @Override
  public boolean equals( Object obj ) {

    if ( obj instanceof CatalogItem ) {
      CatalogItem item = (CatalogItem) obj;

      return msUid == null? item.msUid == null:msUid.equals( item.msUid );
    } else if ( obj instanceof String ) {
      String uid = (String) obj;

      return uid.equals( msUid );
    }

    return false;
  }

  public boolean hasColors(){
    return mColors != null && mColors.length > 0;
  }

  public int getPrice() {

    return miPrice;
  }

  public void setPrice( int price ) {

    miPrice = price;
  }

  public int getThumbnail() {

    return miThumbnail;
  }

  public void setThumbnail( int thumbnail ) {

    miThumbnail = thumbnail;
  }

  public String getName() {

    return msName;
  }

  public void setName( String name ) {

    msName = name;
  }

  public String getUid() {

    return msUid;
  }

  public void setUid( String uid ) {

    msUid = uid;
  }

  public String getDescriptionLong() {

    return msDescriptionLong;
  }

  public void setDescriptionLong( String descriptionLong ) {

    msDescriptionLong = descriptionLong;
  }

  public String getDescriptionShort() {

    return msDescriptionShort;
  }

  public void setDescriptionShort( String descriptionShort ) {

    msDescriptionShort = descriptionShort;
  }

  public String getSize() {

    return msSize;
  }

  public void setSize( String size ) {

    msSize = size;
  }

  public int[] getColors() {

    return mColors;
  }

  public void setColors( int[] colors ) {

    mColors = colors;
  }
}
