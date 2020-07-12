package com.techieasif.miwok;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CustomFragmentPageAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public CustomFragmentPageAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mContext = context;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new NumbersFragment();
        }else if(position == 1){
            return new ColorsFragment();
        }else if (position == 2){
            return new FamilyFragment();
        }else{
            return new PhrasesFragment();
        }

    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 4;
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return mContext.getString(R.string.category_numbers);
        }else  if( position ==1){
            return mContext.getString(R.string.category_colors);
        }else if( position == 2){
            return mContext.getString(R.string.category_family);
        }else{
            return mContext.getString(R.string.category_phrases);
        }
    }
}
