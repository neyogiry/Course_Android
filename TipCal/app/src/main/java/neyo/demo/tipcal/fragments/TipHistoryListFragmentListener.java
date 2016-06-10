package neyo.demo.tipcal.fragments;

import neyo.demo.tipcal.models.TipRecord;

/**
 * Created by Neyo
 */
public interface TipHistoryListFragmentListener {

    void addToList(TipRecord record);
    void clearList();
}
