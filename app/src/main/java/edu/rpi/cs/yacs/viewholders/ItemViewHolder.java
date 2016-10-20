package edu.rpi.cs.yacs.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.rpi.cs.yacs.R;

/**
 * Created by Mark Robinson on 10/15/16.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.name)
    TextView nameView;

    @BindView(R.id.code)
    TextView codeView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(String name, String code){
        nameView.setText(name);
        codeView.setText(code);
    }
}