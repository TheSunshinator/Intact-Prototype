package com.sunshinator.intactprototype;

import android.support.annotation.CallSuper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.sunshinator.intactprototype.ItemAdapter.Clicked;

/**
 * ViewHolder for CatalogItem adapters
 * <p>
 * Created by The Sunshinator on 06/05/2017.
 */
public abstract class ViewHolder
    extends RecyclerView.ViewHolder {

  private CatalogItem mItem;

  public ViewHolder( View itemView, final Clicked listener ) {

    super( itemView );

    itemView.setOnClickListener( new OnClickListener() {
      @Override
      public void onClick( View view ) {

        listener.onItemClicked( mItem );
      }
    } );
  }

  @CallSuper
  public void setToItem( CatalogItem item ) {

    mItem = item;
  }

  public static class Catalog
      extends ViewHolder {

    @BindView( R.id.thumbnail )
    ImageView mvThumbnail;

    @BindView( R.id.name )
    TextView mvName;

    public Catalog( View itemView, Clicked listener ) {

      super( itemView, listener );
      ButterKnife.bind( this, itemView );

    }

    @CallSuper
    public void setToItem( CatalogItem item ) {

      super.setToItem( item );

      Picasso.with( mvThumbnail.getContext() )
             .load( item.getThumbnail() )
             .into( mvThumbnail );

      mvName.setText( item.getName() );

    }
  }

  public static class WishList
      extends Catalog {

    @BindView( R.id.price )
    TextView mvPrice;

    @BindView( R.id.description )
    TextView mvDescription;

    @BindView( R.id.out_of_stock )
    View mvStock;

    @BindView( R.id.colors )
    ViewGroup mvColors;

    public WishList( View itemView, Clicked listener ) {

      super( itemView, listener );
      ButterKnife.bind( this, itemView );

    }

    public void setToItem( CatalogItem item ) {

      super.setToItem( item );

      mvPrice.setText(
          String.format( Utils.getLocale( mvPrice.getContext() ), "$ %d", item.getPrice() ) );

      mvDescription.setText( item.getDescriptionShort() );

      mvColors.removeAllViews();
      if ( item.hasColors() ) {
        mvStock.setVisibility( View.GONE );

        for ( int color : item.getColors() ) {
          CardView view = (CardView) LayoutInflater.from( mvColors.getContext() )
                                                   .inflate( R.layout.item_color, mvColors, false );

          view.setCardBackgroundColor( color );
          mvColors.addView( view );
        }
      } else {
        mvStock.setVisibility( View.VISIBLE );
      }
    }

  }

}
