package com.sunshinator.intactprototype;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sunshinator.intactprototype.ItemAdapter.Clicked;
import java.util.List;

public class CatalogActivity
    extends AppCompatActivity {

  @BindView( R.id.list_catalog )
  RecyclerView mvCatalog;

  @BindView( R.id.wish_list )
  RecyclerView mvWishlist;

  @BindView( R.id.total )
  TextView mvTotal;

  @BindView( R.id.subtotal )
  TextView mvSubtotal;

  @Override
  protected void onCreate( Bundle savedInstanceState ) {

    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_catalog );
    ButterKnife.bind( this );

    mvCatalog.setAdapter( new ItemAdapter.Catalog( DatabaseSimulator.getAllItems(), l_OnItemClicked ) );

    initActionBar();

  }

  @Override
  protected void onResume() {

    super.onResume();

    final List<CatalogItem> wishList = DatabaseSimulator.getWishList();
    mvWishlist.setAdapter( new ItemAdapter.WishList( wishList, l_OnItemClicked ) );

    setWishlistTotal( wishList );

  }

  private void initActionBar() {

    Toolbar tb = (Toolbar) findViewById( R.id.toolbar );
    setSupportActionBar( tb );

    final ActionBar ab = getSupportActionBar();
    if ( ab != null ) {
      ab.setDisplayShowCustomEnabled( true );
      ab.setDisplayShowTitleEnabled( false );
    }

  }

  private void setWishlistTotal( List<CatalogItem> wishList ) {

    int total = 0;
    for ( CatalogItem catalogItem : wishList ) {
      total += catalogItem.getPrice();
    }
    mvTotal.setText( getString( R.string.message_total, total ) );
    mvSubtotal.setText( String.format( Utils.getLocale( this ), "$ %d", total ) );
  }

  public void onCheckout( View v ) {

    AlertDialog.Builder builder = new AlertDialog.Builder( this );
    builder.setMessage( R.string.message_checkout )
           .setPositiveButton( R.string.btn_affirmative, new OnClickListener() {
             @Override
             public void onClick( DialogInterface dialogInterface, int i ) {
               // Not required by specs
             }
           } )
           .setNegativeButton( R.string.btn_negative, new OnClickListener() {
             @Override
             public void onClick( DialogInterface dialogInterface, int i ) {

               dialogInterface.dismiss();
             }
           } );

    builder.create().show();
  }

  private final ItemAdapter.Clicked l_OnItemClicked = new Clicked() {
    @Override
    public void onItemClicked( CatalogItem item ) {

      Intent intent = new Intent( CatalogActivity.this, Details.class );
      intent.putExtra( Details.INTENT_KEY_ITEM, item.getUid() );
      startActivity( intent );

    }
  };

}
