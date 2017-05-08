package com.sunshinator.intactprototype;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapters for catalog items
 * <p>
 * Created by The Sunshinator on 06/05/2017.
 */
public abstract class ItemAdapter<T extends ViewHolder>
    extends RecyclerView.Adapter<T> {

  private List<CatalogItem> mValues = new ArrayList<>();
  protected Clicked l_OnItemClicked;

  public ItemAdapter( List<CatalogItem> values, Clicked listener ) {

    mValues = values;
    l_OnItemClicked = listener;
  }

  @Override
  public int getItemCount() {

    return mValues.size();
  }

  @Override
  public void onBindViewHolder( T holder, int position ) {

    holder.setToItem( mValues.get( position ) );

  }

  public static class Catalog
      extends ItemAdapter<ViewHolder.Catalog> {

    public Catalog( List<CatalogItem> values, Clicked listener ) {

      super( values, listener );
    }

    @Override
    public ViewHolder.Catalog onCreateViewHolder( ViewGroup parent, int viewType ) {

      View view = LayoutInflater.from( parent.getContext() )
                                .inflate( R.layout.item_catalog, parent, false );

      return new ViewHolder.Catalog( view, l_OnItemClicked );
    }
  }

  public static class WishList
      extends ItemAdapter<ViewHolder.WishList> {

    public WishList( List<CatalogItem> values, Clicked listener ) {

      super( values, listener );
    }

    @Override
    public ViewHolder.WishList onCreateViewHolder( ViewGroup parent, int viewType ) {

      View view = LayoutInflater.from( parent.getContext() )
                                .inflate( R.layout.item_wishlist, parent, false );

      return new ViewHolder.WishList( view, l_OnItemClicked );
    }
  }

  public interface Clicked {

    void onItemClicked( CatalogItem item );
  }
}
