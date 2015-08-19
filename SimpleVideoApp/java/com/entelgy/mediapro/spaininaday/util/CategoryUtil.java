package com.entelgy.mediapro.spaininaday.util;


import android.os.AsyncTask;
import android.util.Log;

import com.entelgy.mediapro.spaininaday.rest.Category;
import com.entelgy.mediapro.spaininaday.rest.CategoryRequest;
import com.entelgy.mediapro.spaininaday.rest.Subcategory;

public class CategoryUtil {
    private static CategoryUtil ourInstance = new CategoryUtil();
    public static final String CATEGORIES_PATH = "categories?currentPage=1&maxSize=1000";
    public static final String TAG = "CategoryUtil";
    private Category[] categories = new Category[]{};

    public static CategoryUtil getInstance() {
        return ourInstance;
    }

    private CategoryUtil() {
        HttpGetCategoriesTask task = new HttpGetCategoriesTask();
        task.execute();
    }

    private class HttpGetCategoriesTask extends AsyncTask<Void, Void, CategoryRequest> {

        @Override
        protected CategoryRequest doInBackground(Void... params) {
            try {
                CategoryRequest result = (CategoryRequest) RestUtil.getInstance().get(RestUtil.HOST + CATEGORIES_PATH,
                        CategoryRequest.class, null);
                return result;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(CategoryRequest categories) {
            Log.d(TAG, "onPostExcecute result: " + categories);

            if (categories == null) {
                // Error recuperando categorías
                setCategories(new Category[]{});
            } else {
                setCategories(categories.getData());
            }
        }
    }



    /**
     * Returns subcategories from a given category
     * @param categoryName
     * @return Array of Subcategory
     */
    public Subcategory[] getSubcategories(String categoryName) {
        Subcategory[] result = new Subcategory[]{};

        Category[] categories = getCategories();
        for (int i=0; i < categories.length; i++) {
            Category category = categories[i];
            if (category.getName().equalsIgnoreCase(categoryName)) {
                result = category.getSubcategories();
                break;
            }
        }

        return result;
    }

    public Category[] getCategories() {
        return categories;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }
}
