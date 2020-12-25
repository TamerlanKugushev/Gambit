package com.example.gambit;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Product> productList;
    private SharedPreferences preferencesBasket;
    private SharedPreferences preferencesPlus;
    private SharedPreferences preferencesMinus;
    private SharedPreferences preferencesSum;
    private SharedPreferences preferencesNumberSum;
    private SharedPreferences preferencesImageLike;

    public RecyclerAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view=LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.layout_item, parent, false );
        ViewHolder vh=new ViewHolder ( view );
        if (preferencesBasket == null) {
            preferencesBasket=parent.getContext ().getSharedPreferences ( "BASKET", Context.MODE_PRIVATE );
        }
        if (preferencesPlus == null) {
            preferencesPlus=parent.getContext ().getSharedPreferences ( "PLUS", Context.MODE_PRIVATE );
        }
        if (preferencesMinus == null) {
            preferencesMinus=parent.getContext ().getSharedPreferences ( "MINUS", Context.MODE_PRIVATE );
        }
        if (preferencesSum == null) {
            preferencesSum=parent.getContext ().getSharedPreferences ( "SUM", Context.MODE_PRIVATE );
        }
        if (preferencesNumberSum == null) {
            preferencesNumberSum=parent.getContext ().getSharedPreferences ( "NUMBER_SUM", Context.MODE_PRIVATE );
        }
        if (preferencesImageLike == null) {
            preferencesImageLike=parent.getContext ().getSharedPreferences ( "IMAGE_LIKE", Context.MODE_PRIVATE );
        }
        return vh;
    }


    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {
        holder.bind ( productList.get ( position ) );
    }

    @Override
    public int getItemCount() {
        return productList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageFood;
        private TextView titleFood;
        private Button btnPrice;
        private Button btnPlus;
        private Button btnMinus;
        private TextView textSum;
        private Button btnBasket;
        private Product currentProduct;
        boolean basket;
        boolean plus;
        boolean minus;
        boolean sum;
        boolean imageLikeBool;
        int number=1;
        private ImageView imageLike;
        private SwipeLayout swipeLayout;


        public ViewHolder( @NonNull View itemView ) {
            super ( itemView );
            imageFood=itemView.findViewById ( R.id.imageFood );
            titleFood=itemView.findViewById ( R.id.titleFood );
            btnPrice=itemView.findViewById ( R.id.btn_price );
            btnMinus=itemView.findViewById ( R.id.btnMinus );
            btnPlus=itemView.findViewById ( R.id.btnPlus );
            textSum=itemView.findViewById ( R.id.textSum );
            btnBasket=itemView.findViewById ( R.id.btnBasket );
            imageLike=itemView.findViewById ( R.id.trash );
            swipeLayout=itemView.findViewById ( R.id.swipe );

            btnBasketClick ();
            swipeLayoutForRecItem ();
        }

        public void btnBasketClick() {
            btnBasket.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View view ) {
                    if (preferencesBasket.getBoolean ( String.valueOf ( currentProduct.getId () ), false )) {
                        btnBasket.setVisibility ( View.VISIBLE );
                        saveDataBtnBasket ( String.valueOf ( currentProduct.getId () ), false );

                        btnPlus.setVisibility ( View.VISIBLE );
                        saveDataPlus ( String.valueOf ( currentProduct.getId () ), false );

                        btnMinus.setVisibility ( View.VISIBLE );
                        saveDataMinus ( String.valueOf ( currentProduct.getId () ), false );

                        textSum.setVisibility ( View.VISIBLE );
                        saveDataTextSum ( String.valueOf ( currentProduct.getId () ), false );
                    } else {
                        btnBasket.setVisibility ( View.GONE );
                        saveDataBtnBasket ( String.valueOf ( currentProduct.getId () ), true );

                        btnPlus.setVisibility ( View.VISIBLE );
                        saveDataPlus ( String.valueOf ( currentProduct.getId () ), true );

                        btnMinus.setVisibility ( View.VISIBLE );
                        saveDataMinus ( String.valueOf ( currentProduct.getId () ), true );

                        textSum.setVisibility ( View.VISIBLE );
                        textSum.setText ( String.valueOf ( 1 ) );
                        saveDataTextSum ( String.valueOf ( currentProduct.getId () ), true );

                    }
                }
            } );
        }

        public void bind( Product product) {
            currentProduct = product;
            basket=preferencesBasket.getBoolean ( String.valueOf ( currentProduct.getId () ), true );
            plus=preferencesPlus.getBoolean ( String.valueOf ( currentProduct.getId () ), true );
            minus=preferencesMinus.getBoolean ( String.valueOf ( currentProduct.getId () ), true );
            sum=preferencesSum.getBoolean ( String.valueOf ( currentProduct.getId () ), true );
            number=preferencesNumberSum.getInt ( String.valueOf ( currentProduct.getId () ), 1 );
            textSum.setText ( String.valueOf ( number ) );
            imageLikeBool=preferencesImageLike.getBoolean ( String.valueOf ( currentProduct.getId () ), true );


            String imageUrl= product.getImage ();
            Picasso.with ( itemView.getContext () )
                    .load ( imageUrl )
                    .into ( imageFood );
            titleFood.setText ( product.getName () );
            btnPrice.setText ( String.valueOf ( product.getPrice () + "₽" ) );

            if (preferencesBasket.getBoolean ( String.valueOf ( currentProduct.getId () ), false )) {
                btnBasket.setVisibility ( View.GONE );
            } else {
                btnBasket.setVisibility ( View.VISIBLE );
            }

            if (preferencesPlus.getBoolean ( String.valueOf ( currentProduct.getId () ), false )) {
                btnPlus.setVisibility ( View.VISIBLE );
            } else {
                btnPlus.setVisibility ( View.GONE );
            }

            if (preferencesPlus.getBoolean ( String.valueOf ( currentProduct.getId () ), false )) {
                btnMinus.setVisibility ( View.VISIBLE );
            } else {
                btnMinus.setVisibility ( View.GONE );
            }

            if (preferencesSum.getBoolean ( String.valueOf ( currentProduct.getId () ), false )) {
                textSum.setVisibility ( View.VISIBLE );
            } else {
                textSum.setVisibility ( View.GONE );
            }

            if (preferencesImageLike.getBoolean ( String.valueOf ( currentProduct.getId () ), imageLikeBool )) {
                imageLike.setImageResource ( R.drawable.ic_unlike );
            } else {
                imageLike.setImageResource ( R.drawable.ic_like );
            }

            clickerPlus ();
            clickerMinus ();

        }

        public void saveDataBtnBasket( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor=preferencesBasket.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataPlus( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor=preferencesPlus.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataMinus( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor=preferencesMinus.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataTextSum( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor=preferencesSum.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataNumberSum( String id, int dataToSave ) {
            SharedPreferences.Editor editor=preferencesNumberSum.edit ();
            editor.putInt ( id, dataToSave );
            editor.apply ();
        }

        public void saveDataImageLike( String id, boolean dataToSave ) {
            SharedPreferences.Editor editor=preferencesImageLike.edit ();
            editor.putBoolean ( id, dataToSave );
            editor.apply ();
        }


        public void swipeLayoutForRecItem() {
            swipeLayout.setShowMode ( SwipeLayout.ShowMode.PullOut );
            swipeLayout.addDrag ( SwipeLayout.DragEdge.Right, swipeLayout.findViewById ( R.id.go ) );
            swipeLayout.addSwipeListener ( new SwipeLayout.SwipeListener () {
                @Override
                public void onStartOpen( SwipeLayout layout ) {

                }

                @Override
                public void onOpen( SwipeLayout layout ) {
                    if (imageLikeBool) {
                        imageLike.setImageResource ( R.drawable.ic_like );
                        imageLikeBool = false;

                    } else {
                        imageLike.setImageResource ( R.drawable.ic_unlike );
                        imageLikeBool = true;
                    }
                    saveDataImageLike ( String.valueOf ( currentProduct.getId () ), imageLikeBool );
                }

                @Override
                public void onStartClose( SwipeLayout layout ) {

                }

                @Override
                public void onClose( SwipeLayout layout ) {

                }

                @Override
                public void onUpdate( SwipeLayout layout, int leftOffset, int topOffset ) {

                }

                @Override
                public void onHandRelease( SwipeLayout layout, float xvel, float yvel ) {
                    layout.postDelayed ( new Runnable () {
                        @Override
                        public void run() {
                            layout.close ();
                        }
                    }, 600 );
                }
            } );
        }

        public void clickerPlus() {
            btnPlus.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View view ) {
                    number=Integer.parseInt ( textSum.getText ().toString () ) + 1;
                    textSum.setText ( String.valueOf ( number ) );
                    saveDataNumberSum ( String.valueOf ( currentProduct.getId () ), number );
                }
            } );
        }

        public void clickerMinus() {
            btnMinus.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View view ) {
                    if (Integer.parseInt ( textSum.getText ().toString () ) > 0) {
                        number=Integer.parseInt ( textSum.getText ().toString () ) - 1;
                        textSum.setText ( String.valueOf ( number ) );
                        saveDataNumberSum ( String.valueOf ( currentProduct.getId () ), number );

                        if (Integer.parseInt ( textSum.getText ().toString () ) == 0) {
                            textSum.setVisibility ( View.GONE );
                            btnPlus.setVisibility ( View.GONE );
                            btnMinus.setVisibility ( View.GONE );

                            number=1;

                            btnBasket.setVisibility ( View.VISIBLE );
                            saveDataBtnBasket ( String.valueOf ( currentProduct.getId () ), false );
                            saveDataNumberSum ( String.valueOf ( currentProduct.getId () ), number );
                            saveDataPlus ( String.valueOf ( currentProduct.getId () ), false );
                            saveDataMinus ( String.valueOf ( currentProduct.getId () ), false );
                        }
                    }
                }
            } );
        }
    }

}
