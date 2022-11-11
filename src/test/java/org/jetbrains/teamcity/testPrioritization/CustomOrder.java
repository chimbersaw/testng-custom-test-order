package org.jetbrains.teamcity.testPrioritization;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomOrder implements IMethodInterceptor {
    private static final String TEST_PRIORITIZATION_CONFIG = "/test-prioritization-config.txt";

    private double toRatioValue(String s) {
        String[] splitFraction = s.split("/");
        if (splitFraction.length == 2) {
            double successful = Integer.parseInt(splitFraction[0]);
            double all = Integer.parseInt(splitFraction[1]);
            return successful / all;
        } else {
            return 0.0;
        }
    }

    Map<String, Double> successProbability = new HashMap<>();

    {
        try {
            InputStream config = this.getClass().getResourceAsStream(TEST_PRIORITIZATION_CONFIG);
            if (config != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(config));
                while (reader.ready()) {
                    String line = reader.readLine();
                    int lastColon = line.lastIndexOf(":");
                    if (lastColon >= 0) {
                        String name = line.substring(0, lastColon);
                        String ratio = line.substring(lastColon + 1);
                        successProbability.put(name, toRatioValue(ratio));
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }

    private String qualifiedName(ITestNGMethod method) {
        return method.getTestClass().getName() + "." + method.getMethodName();
    }

    private double getMethodSuccessProbability(IMethodInstance instance) {
        return successProbability.getOrDefault(qualifiedName(instance.getMethod()), 0.0);
    }

    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        if (!successProbability.isEmpty()) {
            methods.sort(Comparator.comparingDouble(this::getMethodSuccessProbability));
        }

        return methods;
    }
}
