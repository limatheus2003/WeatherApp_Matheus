package com.matheus.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Declaração dos componentes da interface
    private TextView txtCidade, txtTemperatura, txtDescricao;
    private EditText editCidade;
    private Button btnBuscar, btnSobre;

    // Inicialização do leitor de QR Code
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    String urlLida = result.getContents();
                    Log.d("DEBUG_APP", "QR Code lido: " + urlLida);
                    buscarClima(urlLida);
                } else {
                    Toast.makeText(this, "Leitura cancelada.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular elementos do layout
        txtCidade = findViewById(R.id.txtCidade);
        txtTemperatura = findViewById(R.id.txtTemperatura);
        txtDescricao = findViewById(R.id.txtDescricao);
        editCidade = findViewById(R.id.editCidade);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnSobre = findViewById(R.id.btnSobre);
        FloatingActionButton fabScan = findViewById(R.id.fabScan);

        // Botão "Buscar Clima"
        btnBuscar.setOnClickListener(v -> {
            String cidadeDigitada = editCidade.getText().toString().trim();

            if (!cidadeDigitada.isEmpty()) {
                try {
                    // Codifica corretamente a cidade (para nomes com acentos e espaços)
                    String cidadeFormatada = java.net.URLEncoder.encode(cidadeDigitada, "UTF-8");

                    // 🔧 URL corrigida da API HG Brasil
                    String url = "https://api.hgbrasil.com/weather?format=json"
                            + "&key=2d6b6f07" // chave pública de exemplo
                            + "&city_name=" + cidadeFormatada + ",PR"; // inclui o estado PR

                    Log.d("DEBUG_APP", "Buscando clima com URL: " + url);

                    buscarClima(url);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Erro ao formatar o nome da cidade.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Digite o nome de uma cidade.", Toast.LENGTH_SHORT).show();
            }
        });

        // Botão "Sobre"
        btnSobre.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SobreActivity.class);
            startActivity(intent);
        });

        // Botão flutuante (QR Code)
        fabScan.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Aponte para o QR Code da cidade");
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setCaptureActivity(CaptureActivityPortrait.class);
            barcodeLauncher.launch(options);
        });
    }

    // Método que busca o clima na API
    private void buscarClima(String urlString) {
        Log.d("DEBUG_APP", "Chamando buscarClima() com URL: " + urlString);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            Log.d("DEBUG_APP", "Código HTTP: " + responseCode);

            if (responseCode != 200) {
                Toast.makeText(this, "Erro HTTP: " + responseCode, Toast.LENGTH_LONG).show();
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Log.d("DEBUG_APP", "Resposta JSON da API: " + response);

            JSONObject json = new JSONObject(response.toString());

            // ✅ Lê corretamente o objeto "results"
            if (json.has("results")) {
                JSONObject results = json.getJSONObject("results");

                String cidade = results.optString("city_name", "Cidade não encontrada");
                String temperatura = results.optString("temp", "--") + "°C";
                String descricao = results.optString("description", "Sem descrição disponível");

                txtCidade.setText("Cidade: " + cidade);
                txtTemperatura.setText("Temperatura: " + temperatura);
                txtDescricao.setText("Condição: " + descricao);

                Log.d("DEBUG_APP", "Clima atualizado com sucesso para: " + cidade);
            } else {
                Toast.makeText(this, "Resposta inesperada da API.", Toast.LENGTH_SHORT).show();
                Log.e("DEBUG_APP", "JSON inesperado: " + json.toString());
            }

        } catch (Exception e) {
            Log.e("DEBUG_APP", "Erro ao buscar clima: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Erro ao buscar informações do clima.", Toast.LENGTH_SHORT).show();
        }
    }
}
