package com.example.loca_market.ui.seller.addOffer;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Offer;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.User;
import com.example.loca_market.ui.client.Notifications.NotificationBody;
import com.example.loca_market.ui.client.Notifications.NotificationRoot;
import com.example.loca_market.ui.client.Notifications.RetrofitCaller;
import com.example.loca_market.ui.seller.adapters.SellerAddOfferSearchProductAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddOfferFragment extends Fragment implements SellerAddOfferSearchProductAdapter.OnProductItemListener {
    public static final String TAG = "AddOfferFragment";
    private TextInputLayout tif_offer_titel, tif_percentage, tif_offer_product, tif_offer_begin_date, tif_offer_end_date;
    private Button b_offer_begin_date, b_add_offer;
    private FirebaseFirestore firebaseFirestore;
    private List<Product> productsListSearch;
    private List<Product> allProducts;
    private RecyclerView productSearchRecyclerView;
    private SellerAddOfferSearchProductAdapter productSearchAdapter;
    private static FirebaseUser currentUser;
    private String b_Date, e_Date;
    private Product productOffer;
    private static final String USERS = "users";
    private static final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private static final CollectionReference usersRef = fdb.collection(USERS);
    private String url = "https://fcm.googleapis.com/";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_offer, container, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // construction du  date picker
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("la date du debut ");
        MaterialDatePicker materialDatePickerBegin = builder.build();


        tif_offer_titel = view.findViewById(R.id.tif_offer_titel);
        tif_percentage = view.findViewById(R.id.tif_percentage);
        tif_offer_product = view.findViewById(R.id.tif_offer_product);

        b_offer_begin_date = view.findViewById(R.id.b_offer_begin_date);
        b_add_offer = view.findViewById(R.id.b_add_offer);
        // afficher le date picker de la date de debut
        b_offer_begin_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePickerBegin.show(getParentFragmentManager(), "date debut ");

            }
        });

        materialDatePickerBegin.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPositiveButtonClick(Object selection) {

                Pair selectedDates = (Pair) materialDatePickerBegin.getSelection();
//              then obtain the startDate & endDate from the range
                final Pair<Date, Date> rangeDate = new Pair<>(new Date((Long) selectedDates.first), new Date((Long) selectedDates.second));
//              assigned variables
                Date startDate = rangeDate.first;
                Date endDate = rangeDate.second;
//              Format the dates in ur desired display mode
                SimpleDateFormat simpleFormat = new SimpleDateFormat("dd MMM yyyy");
//              Display it by setText
                b_offer_begin_date.setText(simpleFormat.format(startDate) + " : " + simpleFormat.format(endDate));
                b_Date = simpleFormat.format(startDate);
                e_Date = simpleFormat.format(endDate);
            }
        });

        // get all products
        firebaseFirestore = FirebaseFirestore.getInstance();
        allProducts = new ArrayList<>();

        firebaseFirestore.collection("products").whereEqualTo("productOwner", currentUser.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        ArrayList<Product> productArrayList = new ArrayList<>();
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        if (!value.isEmpty()) {
                            List<DocumentSnapshot> documentSnapshotList = value.getDocuments();

                            for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                                Product product = documentSnapshot.toObject(Product.class);
                                allProducts.add(product);
                            }


                        }
                    }
                });


        // search products
        tif_offer_product = view.findViewById(R.id.tif_offer_product);
        productsListSearch = new ArrayList<>();
        productSearchRecyclerView = view.findViewById(R.id.offer_product_search_recycler);
        productSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        productSearchAdapter = new SellerAddOfferSearchProductAdapter(getContext(), productsListSearch, this);
        productSearchRecyclerView.setAdapter(productSearchAdapter);

        tif_offer_product.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    productSearchRecyclerView.setAdapter(productSearchAdapter);
                    productsListSearch.clear();
                    productSearchAdapter.notifyDataSetChanged();
                } else {
                    productSearchRecyclerView.setAdapter(productSearchAdapter);
                    productsListSearch.clear();
                    searchProduct(s.toString());
                    productSearchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        b_add_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOffer();
            }
        });
        return view;
    }

    private void addOffer() {

        // ajout de l'offfre dans la base
        Offer offerToAdd = new Offer();

        offerToAdd.setOfferTitel(tif_offer_titel.getEditText().getText().toString().trim());
        offerToAdd.setBeginDate(b_Date);
        offerToAdd.setEndDate(e_Date);
        offerToAdd.setPercentage(Float.parseFloat(tif_percentage.getEditText().getText().toString().trim()));
        offerToAdd.setOfferProduct(productOffer);
        offerToAdd.setProductOfferId(productOffer.getPid());
        Log.e(TAG, "productOfferId: "+productOffer.getPid() );
        offerToAdd.setSellerId(currentUser.getUid());
        addOfferRequest(offerToAdd);
        // modification du produits



    }

    private void updateProductRequest(Offer offer) {

        Product productToUp = offer.getOfferProduct();
        firebaseFirestore.collection("products").document(productToUp.getPid()).update("percentage",offer.getPercentage()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess: to update the product " + productToUp.getName());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: to update the product " + productToUp.getName());

            }
        });



    }

    private void addOfferRequest(Offer offerToAdd) {

        firebaseFirestore.collection("offers").add(offerToAdd).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference reference) {
                updateProductRequest(offerToAdd);
                Snackbar snackbar =   Snackbar.make(getView(),"Offer ajouter  ", Snackbar.LENGTH_SHORT);
                snackbar.show();
                tif_offer_titel.getEditText().setText("");
                tif_offer_product.getEditText().setText("");
                tif_percentage.getEditText().setText("");
                b_offer_begin_date.setText("periode");

                notifyUserByInterest(offerToAdd.getOfferProduct().getCategory());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Snackbar snackbar =   Snackbar.make(getView(),"Nous avons rencontrer un problem lors de l'ajout de l'offre ", Snackbar.LENGTH_SHORT);
                snackbar.show();

            }
        });

    }

    private void searchProduct(String text) {
        productsListSearch.clear();
        if (!text.isEmpty()) {
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(text.toLowerCase())&& product.getPercentage()==0) {
                    productsListSearch.add(product);
                    productSearchAdapter.notifyDataSetChanged();
                }
            }
        }

    }


    private void notifyUserByInterest(String offerCategory ){

        Log.e(TAG, "notifyUserByInterest by "+offerCategory);

        ArrayList<User> userArrayList = new ArrayList<>();
        usersRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                        userArrayList.add(documentSnapshot.toObject(User.class));
                    }

                    ArrayList<User> filteredUserArrayList = new ArrayList<>();
                    Log.e(TAG, "size of non filterd"+userArrayList.size());
                    filteredUserArrayList = filterUsers(userArrayList , offerCategory);
                    Log.e(TAG, "size of filteredUserArrayList "+filteredUserArrayList.size());



                    for (User user:filteredUserArrayList ) {

                        if(user.getNotifToken()!= null && !user.getNotifToken().equals("")){
                            // executer la requete  de notification

                            NotificationBody data = new NotificationBody("Nouvelle offre disponible","Vous avez une nouvelle offre suseptible de vous intéressez dans la catégorie"+offerCategory);
                            NotificationRoot root = new NotificationRoot(data,user.getNotifToken());
                            Gson json = new Gson();
                            String requestBody = json.toJson(root);
                            Log.d(TAG, "NotifyParent: root "+ requestBody);
                            Retrofit r = new Retrofit.Builder().baseUrl(url)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            Log.d(TAG, "NotifyParent: "+r.toString());
                            RetrofitCaller retrofitCaller =r.create(RetrofitCaller.class);
                            Call<NotificationRoot> call = retrofitCaller.PostData(root);
                            call.enqueue(new Callback<NotificationRoot>() {
                                @Override
                                public void onResponse(Call<NotificationRoot> call, retrofit2.Response<NotificationRoot> response) {
                                    Log.d(TAG, "onResponse: "+response.isSuccessful());
                                }

                                @Override
                                public void onFailure(Call<NotificationRoot> call, Throwable t) {

                                }
                            });

                        }

                    }






                }
            }
        });



    }

    private ArrayList<User> filterUsers(ArrayList<User> userArrayList , String offerCategorie) {

        ArrayList<User> filterdUserArrayList = new ArrayList<>();


        for (User user: userArrayList) {

            if (user.getRole().equals("client")&& user.getPreferences()!=null) {

                List<Category> categoryList = user.getPreferences();
                Log.e(TAG, "filterUsers :Categories  size"+categoryList.size() );
                for (Category category : categoryList) {
                    if (category.getName().trim().equals(offerCategorie)) {
                        Log.e(TAG, "filterUsers: "+category.getName()+"=================="+offerCategorie+"******" );
                        filterdUserArrayList.add(user);

                    }

                }
            }
        }

        return  filterdUserArrayList ;
    }

    @Override
    public void onProductClick(int position) {
        Product currentProduct = productsListSearch.get(position);
        productOffer = currentProduct;
        tif_offer_product.getEditText().setText(currentProduct.getName());
        Toast.makeText(getContext(), "cliccke", Toast.LENGTH_SHORT).show();
        productSearchRecyclerView.setAdapter(null);
    }
}
