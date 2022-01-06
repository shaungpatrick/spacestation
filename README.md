# Spacestation
An API that allows users to know if the International Space Station is currently visible on the earth directly beneath it.
For example, if the ISS is currently over Austrailia, this API will tell you if the ISS is visible from Austrailia or not at the time of the request!

## Prerequisites
This API uses weather data from the weatherbit API.
This means that, in order to use this API you will need your own API key from weatherbit.
You can sign up to get your key here: https://www.weatherbit.io/account/create

**NOTE**: Your first key is free for 30 days and can handle up to 1,000 calls/day

## Setup & Installation 
Download the latest version of the API [here](https://github.com/ShaunGPatrick/spacestation/releases/latest)

### Environment variables
The key for the weatherbit API can be set using the env variable ```WEATHER_API_KEY```

This key needs to be set in your ```/etc/environment``` file

Example
```
echo "WEATHER_API_KEY=<Your_Key>" >> /etc/environment
source /etc/environment
```

**NOTE** If you have installed the API before setting the WEATHER_API_KEY env variable, you will need to restart the service
```
sudo systemctl restart spacestation.service
```

### Installation
* RHEL and CentOS 7 or newer
```
sudo yum -y localinstall spacestation*.rpm
```

* Debian 9 and Ubuntu 16.04 or newer
```
sudo alien --scripts spacestation*.rpm
sudo dpkg -i spacestation*_all.deb
```

### Confirm Installation
Confirm the service is running correctly using the following command
```
systemctl status spacestation.service
```

If the service was installed correctly you will see an output like this
```
● spacestation.service - Space Station REST Service
     Loaded: loaded (/etc/systemd/system/spacestation.service; enabled; vendor preset: enabled)
     Active: active (running) since Tue 2022-01-04 18:41:09 GMT; 6s ago
   Main PID: 434672 (java)
      Tasks: 26 (limit: 9269)
     Memory: 263.2M
     CGroup: /system.slice/spacestation.service
             └─434672 /usr/bin/java -jar /com/api/spacestation/spacestation.jar
```

## Example usage
This API runs on port 8080.
To check if the International Space Station is currently visible, use this endpoint
```
http://localhost:8080/space-station/is-visible
```

This endpoint will return 'true' if the International Space Station is currently visible, and 'false' if it is not

## Documentation
Documentation of all endpoints can be viewed from the Swagger UI here
```
http://localhost:8080/swagger-ui/index.html
```

You can also get documentation in JSON format here
```
http://localhost:8080/v2/api-docs
```

## Future features
- [ ] Implement better UI for response. Instead of 'true' or 'false' it would say "YES! The International Space Station is currently visible above Australia".
- [ ] Report the cloud coverage and the time of day for where the ISS is currently positioned over
- [ ] Add POST endpoint to add key for weather API key to service
- [ ] Make application configurations external
