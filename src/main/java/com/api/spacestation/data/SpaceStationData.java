package com.api.spacestation.data;

import com.api.spacestation.keys.SpaceStationKeys;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class SpaceStationData {

    @SerializedName("iss_position")
    private Map<Object, Object> positionData;

    public Map<Object, Object> getPositionData() {
        return positionData;
    }

    public void setPositionData(Map<Object, Object>  positionData) {
        this.positionData = positionData;
    }

    public Object getLatitude() {
        return positionData.get(SpaceStationKeys.LATITUDE);
    }

    public Object getLongitude() {
        return positionData.get(SpaceStationKeys.LONGITUDE);
    }


}
