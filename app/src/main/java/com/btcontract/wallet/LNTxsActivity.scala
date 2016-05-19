package com.btcontract.wallet

import Utils.{none, runAnd}
import android.view.{MenuItem, Menu}
import android.widget.{SearchView, ListView}

import android.os.Bundle
import android.view.MenuItem.OnActionExpandListener
import android.widget.SearchView.OnQueryTextListener


class LNTxsActivity extends InfoActivity with SearchActivity { me =>
  lazy val lnList = findViewById(R.id.lnItemsList).asInstanceOf[ListView]

  // Initialize this activity, method is run once
  override def onCreate(savedState: Bundle) =
  {
    super.onCreate(savedState)
    setContentView(R.layout.activity_ln_txs)
  }

  override def onCreateOptionsMenu(menu: Menu) = runAnd(true) {
    getMenuInflater.inflate(R.menu.ln_transactions_ops, menu)
    me activateMenu menu
  }
}

trait SearchActivity {
  def activateMenu(menu: Menu) = try {
    val item = menu.findItem(R.id.search).getActionView
    val searchView = item.asInstanceOf[SearchView]

    // React to non empty search queries
    searchView setOnQueryTextListener new OnQueryTextListener {
      def onQueryTextChange(query: String) = runAnd(true) { /* none */ }
      def onQueryTextSubmit(query: String) = true
    }

    // Restore default view when search is closed if it was changed
    menu getItem 0 setOnActionExpandListener new OnActionExpandListener {
      def onMenuItemActionCollapse(menu: MenuItem) = runAnd(true) { /* none */ }
      def onMenuItemActionExpand(menu: MenuItem) = true
    }

    // Remove the bottom line from search plate, this may throw on some models
    val searchPlateId = searchView.getResources.getIdentifier("android:id/search_plate", null, null)
    searchView.findViewById(searchPlateId).setBackgroundResource(R.drawable.apptheme_search_view_holo)
  } catch none
}