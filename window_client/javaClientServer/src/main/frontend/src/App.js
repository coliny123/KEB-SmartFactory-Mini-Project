import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
    const [sensorData, setSensorData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            const result = await axios.get('/api/sensor');
            setSensorData(result.data);
        };

        const interval = setInterval(fetchData, 10000); // 10초 간격으로 데이터 업데이트
        return () => clearInterval(interval); // 컴포넌트가 제거되면 인터벌 제거
    }, []);

    return (
        <div className="App">
            {sensorData ? (
                <div>
                    <h1>ESP32 Sensor Data</h1>
                    <pre>{JSON.stringify(sensorData, null, 2)}</pre>
                </div>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
}

export default App;
