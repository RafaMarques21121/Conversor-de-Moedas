package br.com.conversomoeda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainMoeda {
    private static final String API_KEY = "bc51fe8ed28ac942bd8ffc6e";

    public static void main(String[] args) {
        Scanner menu = new Scanner(System.in);

        while (true) {
            System.out.print("|-------------------- converção de " +
                    "------------------|\n");
            System.out.print(" Opção 1 para converter o Real para Dolar\n");
            System.out.print(" Opção 2 para converter o Dolar para Real\n");
            System.out.print(" Opção 3 para sair");
            System.out.print("Digite uma opção:\n");

            int opcao = menu.nextInt();

            if (opcao == 3) {
                System.out.print("\nConversor Finalizado!");
                menu.close();
                break;
            }

            System.out.print("Digite o valor a ser convertido: ");
            double valor = menu.nextDouble();

            switch (opcao) {
                case 1:
                    System.out.printf("%.2f BRL = %.2f USD\n", valor, converterMoeda("BRL", "USD", valor));
                    break;

                case 2:
                    System.out.printf("%.2f USD = %.2f BRL\n", valor, converterMoeda("USD", "BRL", valor));
                    break;

                default:
                    System.out.print("Opção Inválida! tente novamente com uma opção valida!\n");
                    break;
            }
        }
    }

    private static double converterMoeda(String from, String to, double valor) {
        try {
            String url_str = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + from;
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            double taxaDeCambio = jsonobj.getAsJsonObject("conversion_rates").get(to).getAsDouble();

            return valor * taxaDeCambio;
        } catch (Exception e) {
            System.out.println("Erro ao obter taxa de câmbio: " + e.getMessage());
            return 0;
        }
    }
}