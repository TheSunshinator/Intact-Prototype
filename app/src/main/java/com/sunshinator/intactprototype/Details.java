package com.sunshinator.intactprototype;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;

public class Details
    extends AppCompatActivity {

  public static final String INTENT_KEY_ITEM = "item";

  @BindView( R.id.price )
  TextView mvPrice;

  @BindView( R.id.colors )
  LinearLayout mvColors;

  @BindView( R.id.size )
  TextView mvSize;

  @BindView( R.id.wishlist )
  TextView mvWishlist;

  @BindView( R.id.description )
  TextView mvDescription;

  @BindView( R.id.thumbnail )
  ImageView mvThumbnail;

  private CatalogItem mItem;

  @Override
  protected void onCreate( Bundle savedInstanceState ) {

    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_details );
    ButterKnife.bind( this );

    String uid = getIntent().getStringExtra( INTENT_KEY_ITEM );
    mItem = DatabaseSimulator.getItem( uid );

    assert mItem != null;

    initActionBar( mItem.getName() );
    setCatalogItemInLayout();

  }

  /**
   * @param title Title to set in the tool bar
   */
  private void initActionBar( String title ) {

    Toolbar tb = (Toolbar) findViewById( R.id.toolbar );

    TextView vTitle = (TextView) tb.findViewById( R.id.title );
    vTitle.setText( title );

    View vBackBtn = tb.findViewById( R.id.back );
    vBackBtn.setOnClickListener( l_Back );

    setSupportActionBar( tb );

    final ActionBar ab = getSupportActionBar();
    if ( ab != null ) {
      ab.setDisplayShowCustomEnabled( true );
      ab.setDisplayShowTitleEnabled( false );
    }

  }

  /**
   * Set all the info of the CatalogItem to display in the layout
   */
  private void setCatalogItemInLayout() {

    // Thumbnail
    Picasso.with( this )
           .load( mItem.getThumbnail() )
           .into( mvThumbnail );

    mvPrice.setText( String.format( Utils.getLocale( this ), "$ %d", mItem.getPrice() ) );
    mvSize.setText( mItem.getSize() );
    mvDescription.setText( mItem.getDescriptionLong() );

    // Wish list button
    if ( DatabaseSimulator.getWishList().contains( mItem ) ) {
      mvWishlist.setBackgroundResource( R.drawable.bg_btn_undo );
      mvWishlist.setText( R.string.btn_wishlist_remove );
    } else {
      mvWishlist.setBackgroundResource( R.drawable.bg_btn_do );
      mvWishlist.setText( R.string.btn_wishlist_add );
    }

    // Colors
    mvColors.removeAllViews();
    if ( mItem.hasColors() ) {
      for ( int color : mItem.getColors() ) {
        CardView view = (CardView) LayoutInflater.from( mvColors.getContext() )
                                                 .inflate( R.layout.item_color, mvColors, false );

        view.setCardBackgroundColor( color );
        mvColors.addView( view );
      }
    }
  }

  public void onWishlistTriggered( View v ) {

    DatabaseSimulator.triggerWishList( mItem );
    finish();

  }

  private final View.OnClickListener l_Back = new View.OnClickListener() {
    @Override
    public void onClick( View view ) {

      onBackPressed();

    }
  };
}
