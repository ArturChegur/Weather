# Weather Tracker Web Application

This is a Java web application for viewing current weather conditions. Users can register, log in, and manage a list of locations, displaying the weather for each.

## Features

- **Add Locations:** Search for a city and click the "Add" button to include it in your list.
- **Remove Locations:** Click on a city tile in your list to remove it.

## Running the Project with Docker

### Build and Run

1. **Build the Docker Image:**
    ```bash
    docker build -t weather
    ```

2. **Run the Docker Container:**
    ```bash
    docker run -p 8080:8080 weather
    ```

3. **Access the Application:**
   Open your browser and go to:
    ```
    http://localhost:8080/Weather
    ```

## Notes

- Ensure Docker is installed and running on your machine.
- The application fetches weather data from an external API. Make sure to configure API keys if needed.


Project`s technical specifications - https://zhukovsd.github.io/java-backend-learning-course/projects/weather-viewer/

Author - [ArturChegur]
