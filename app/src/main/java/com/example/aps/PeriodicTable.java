package com.example.aps;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicTable
{
    private final ArrayList<PeriodicElement> table = new ArrayList<PeriodicElement>();

    public PeriodicTable(InputStream elementYaml)
    {
        Yaml yaml = new Yaml();
        Map<String, Object> data = (Map<String, Object>) yaml.load(elementYaml);

        short i = 1;
        for (String elementName : (List<String>) data.get("elements"))
        {
            PeriodicElement entry = new PeriodicElement();
            entry.name = StringUtils.capitalize(elementName);
            entry.number = i;
            ++i;
        }
    }

    public PeriodicElement getElementByNumber(short number)
    {
        if (number < 1 || number > 118) {
            return null;
        }
        PeriodicElement requested = this.table.get(number - 1);
        if (!requested.fetched) {
            try {
                String json = readUrl(
                        String.format("https://periodic-table-elements-info.herokuapp.com/element/atomicNumber/%d", number)
                );
                Type type = new TypeToken<List<HashMap<String, Object>>>() {}.getType();
                List<Map<String, Object>> response = new Gson().fromJson(json, type);
                Map<String, Object> element = response.get(0);

                requested.fetched = true;
                requested.symbol = (String) element.get("symbol");
                requested.mass = (String) element.get("atomicMass");
                requested.meltingPoint = (element.get("meltingPoint") instanceof String) ? Float.NaN : (float) element.get("meltingPoint");
                requested.boilingPoint = (element.get("boilingPoint") instanceof String) ? Float.NaN : (float) element.get("boilingPoint");
            } catch (Exception e) {
                return null;
            }
        }
        return requested;
    }

    // copied off stack overflow, as expected
    // screw java and its low level apis
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
