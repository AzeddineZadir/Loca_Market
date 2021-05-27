package com.example.loca_market.ui.client.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;

import com.example.loca_market.R;
import com.example.loca_market.data.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LocalisationFragment extends Fragment {

    private FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    AutoCompleteTextView et_address, et_city, et_postalCode, et_country;
    User currentUser = null;

    public LocalisationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_localisation, container, false);
        et_address = view.findViewById(R.id.et_address_localisation);
        et_city = view.findViewById(R.id.et_localisation_city);
        et_postalCode = view.findViewById(R.id.et_postal_code_localisation);
        et_country = view.findViewById(R.id.et_country_localisation);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        insertData();


        contryAutocomplet();
        contryVille();
        return view;
    }

    private void insertData() {
        mStore.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
                if (currentUser != null) {
                    if (currentUser.getAddress() != null && !currentUser.getAddress().isEmpty())
                        et_address.setText(currentUser.getAddress());
                    if (currentUser.getCity() != null && !currentUser.getCity().isEmpty())
                        et_city.setText(currentUser.getCity());
                    if (currentUser.getPostalCode() != null && !currentUser.getPostalCode().isEmpty())
                        et_postalCode.setText(currentUser.getPostalCode());
                    if (currentUser.getCountry() != null && !currentUser.getCountry().isEmpty())
                        et_country.setText(currentUser.getCountry());
                }
            }
        });
    }

    public void saveData() {

        Map<String, Object> mapInfo = new HashMap<>();
        if (et_address != null && !et_address.getText().toString().trim().isEmpty()) {
            mapInfo.put("address", et_address.getText().toString().trim());
        }
        if (et_city != null && !et_city.getText().toString().trim().isEmpty()) {
            mapInfo.put("city", et_city.getText().toString().trim());
        }
        if (et_postalCode != null && !et_postalCode.getText().toString().trim().isEmpty()) {
            mapInfo.put("postalCode", et_postalCode.getText().toString().trim());
        }
        if (et_country != null && !et_country.getText().toString().trim().isEmpty()) {
            mapInfo.put("country", et_country.getText().toString().trim());
        }
        if (!mapInfo.isEmpty())
            mStore.collection("users").document(mAuth.getCurrentUser().getUid()).update(mapInfo);
    }

    private void contryAutocomplet() {
        String[] countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
                "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia",
                "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin",
                "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil",
                "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia",
                "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
                "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the",
                "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark",
                "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
                "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France",
                "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia",
                "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala",
                "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)",
                "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland",
                "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, " +
                "Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic",
                "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg",
                "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali",
                "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia," +
                " Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique",
                "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand",
                "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman",
                "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland",
                "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis",
                "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia",
                "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands",
                "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena",
                "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland",
                "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo",
                "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu",
                "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands",
                "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis" +
                " and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine"};

        List<String> contrylist = Arrays.asList(countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, contrylist);
        et_country.setAdapter(adapter);

    }

    private void contryVille() {
        String[] villes = new String[]{"Paris","Nice","Toulouse","Marseille","Rennes","Grenoble","Nantes","Montpellier",
                "Lyon","Rouen","Strasbourg","Nancy","Metz","Brest","Mulhouse","Limoges","Orléans","Bordeaux","Le Havre","Lille",
                "Reims","Saint-Étienne","Toulon","Dijon","Angers","Nîmes","Villeurbanne","Clermont-Ferrand","Le Mans","Aix-en-Provence",
                "Amiens","Tours","Boulogne-sur-Mer","Annecy","Perpignan","Boulogne-Billancourt","Besançon","Belfort","Saint-Denis",
                "Argenteuil","Montreuil","Caen","Roubaix","Tourcoing","Nanterre","Avignon","Vitry-sur-Seine"
                ,"Épinal","Créteil","Poitiers","Dunkerque","Aubervilliers","Versailles","Aulnay-sous-Bois","Carrières-sur-Seine",
                "Colombes","Courbevoic","Rueil-Malmaison","Champigny-sur-Marne","Béziers","Pau","La Rochelle","Saint-Maur-des-Fossés"
                ,"Calais","Cannes","Antibes","Drancy","Ajaccio","Mérignac","Saint-Nazaire","Colmar","Issy-les-Moulineaux",
                "Noisy-le-Grand","Courcouronnes","Vénissieux","Cergy","Bourges","Levallois-Perret","La Seyne-sur-Mer",
                "Pessac","Valence","Quimper","Antony","Ivry-sur-Seine","Montélimar","Troyes","Clichy","Cherbourg","Montauban"
                ,"Neuilly-sur-Seine","Chambéry","Niort","Sarcelles","Pantin","Lorient","Le Blanc-Mesnil","Beauvais","Maisons-Alfort"
                ,"Hyères","Épinay-sur-Seine","Meaux","Chelles","Villejuif","Narbonne","La Roche-sur-Yon","Cholet","Saint-Quentin",
                "Bobigny","Bondy","Vannes","Clamart","Fontenay-sous-Bois","Fréjus","Arles","Sartrouville","Corbeil-Essonnes","Bayonne"
                ,"Saint-Ouen","Sevran","Cagnes-sur-Mer","Massy","Grasse","Montrouge","Vincennes","Vaulx-en-Velin","Laval","Suresnes",
                "Albi","Martigues","Évreux","Brive-la-Gaillarde","Gennevilliers","Charleville-Mézières"
                ,"Saint-Herblain","Aubagne","Rosny-sous-Bois","Saint-Priest","Saint-Malo","Blois","Carcassonne","Bastia",
                "Salon-de-Provence","Meudon","Choisy-le-Roi","Chalon-sur-Saône","Châlons-en-Champagne","Saint-Germain-en-Laye",
                "Puteaux","Livry-Gargan","Saint-Brieuc","Mantes-la-Jolie","Noisy-le-Sec","Les Sables-d’Olonne","Alfortville",
                "Châteauroux","Valenciennes","Sète","Caluire-et-Cuire","Istres","La Courneuve","Garges-lès-Gonesse","Talence",
                "Angoulême","Castres","Bron","Bourg-en-Bresse","Tarbes","Le Cannet","Rezé","Arras","Wattrelos","Bagneux","Gap",
                "Thionville","Alès","Compiègne","Melun","Douai","Gagny","Draguignan","Colomiers","Anglet","Stains","Marcq-en-Baroeul",
                "Chartres","Saint-Martin-d’Hères","Joué-lés-Tours","Poissy","Châtillon","Villefranche-sur-Saône","Échirolles","Villepinte",
                "Franconville","Savigny-sur-Orge","Sainte-Geneviève-des-Bois"
        };

        List<String> villeslist = Arrays.asList(villes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, villeslist);
        et_city.setAdapter(adapter);

    }
}