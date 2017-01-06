package org.ncibi.ws.model;

import java.util.HashMap;
import java.util.Map;

public class NcibiWSResponse<T>
{
    private final Map<String, String> parameterSet = new HashMap<String, String>();
    private final String application;
    private static final String supportStatement = "Supported by the National Institutes of Health as part of the NIH\'s National Center for Integrative Biomedical Informatics (NCIBI)";
    private static final String grantNumber = "U54 DA021519";
    private static final String supportDetails = "http://www.ncibi.org";
    private static final String copyrightStatement = "Copyright 2010 by the Regents of the University of Michigan";
    private static final String copyrightYear = "2010";
    private static final String copyrightDetails = "http://mimi.ncibi.org/MimiWeb/AboutPage.html#licensing";
    private final T results;
    private String badRequestResponse;
    private final Map<Integer, String> errorCodes = new HashMap<Integer, String>();
    private int currentErrorCode;
    private Boolean resultTag = false;

    public NcibiWSResponse(String application, T results)
    {
        this.application = application;
        this.results = results;
    }

    public Integer getCurrentErrorCode()
    {
        return currentErrorCode;
    }

    public String getErrorMessage()
    {
        return errorCodes.get(currentErrorCode);
    }

    public void setCurrentErrorCode(Integer currentErrorCode)
    {
        this.currentErrorCode = currentErrorCode;
    }

    public Map<Integer, String> getErrorCodes()
    {
        return errorCodes;
    }

    public void addErrorCode(Integer errorNumber, String errorMessage)
    {
        this.errorCodes.put(errorNumber, errorMessage);
    }

    public String getBadRequestResponse()
    {
        return badRequestResponse;
    }

    public void setBadRequestResponse(String badRequestResponse)
    {
        this.badRequestResponse = badRequestResponse;
    }

    public Boolean getResultTag()
    {
        return resultTag;
    }

    public void setResultTag(Boolean resultTag)
    {
        this.resultTag = resultTag;
    }

    public String getApplication()
    {
        return application;
    }

    public Map<String, String> getParameterSet()
    {
        return parameterSet;
    }
    
    public void addParameter(String parameterName, String parameterValue)
    {
        parameterSet.put(parameterName, parameterValue);
    }

    public String getCopyrightDetails()
    {
        return copyrightDetails;
    }
    
    public T getResults()
    {
        return results;
    }

    public String getSupportStatement()
    {
        return supportStatement;
    }

    public String getGrantNumber()
    {
        return grantNumber;
    }

    public String getSupportDetails()
    {
        return supportDetails;
    }

    public String getCopyrightStatement()
    {
        return copyrightStatement;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }
}
