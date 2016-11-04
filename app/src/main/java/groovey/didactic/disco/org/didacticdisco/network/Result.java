package groovey.didactic.disco.org.didacticdisco.network;


import java.util.ArrayList;
import groovey.didactic.disco.org.didacticdisco.models.Line;

/**
 *  {"result":[
 *  {"model": "draw.data", "pk": 1427, "fields": {"latStart": 52.54464782, "lonStart": 13.35313508, "latEnd": 52.54464781, "lonEnd": 13.35313508, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252733}},
 *  {"model": "draw.data", "pk": 1428, "fields": {"latStart": 52.54463464, "lonStart": 13.35312013, "latEnd": 52.54463463, "lonEnd": 13.35312013, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252733}},
 *  {"model": "draw.data", "pk": 1429, "fields": {"latStart": 52.54463463, "lonStart": 13.35312013, "latEnd": 52.54463462, "lonEnd": 13.35312014, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252734}},
 *  {"model": "draw.data", "pk": 1430, "fields": {"latStart": 52.54464781, "lonStart": 13.35313508, "latEnd": 52.54464781, "lonEnd": 13.35313507, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252734}},
 *  {"model": "draw.data", "pk": 1431, "fields": {"latStart": 52.54463462, "lonStart": 13.35312014, "latEnd": 52.54463462, "lonEnd": 13.35312014, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252735}},
 *  {"model": "draw.data", "pk": 1432, "fields": {"latStart": 52.54464781, "lonStart": 13.35313507, "latEnd": 52.54464781, "lonEnd": 13.35313507, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252735}},
 *  {"model": "draw.data", "pk": 1433, "fields": {"latStart": 52.54463462, "lonStart": 13.35312014, "latEnd": 52.54463462, "lonEnd": 13.35312013, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252736}},
 *  {"model": "draw.data", "pk": 1434, "fields": {"latStart": 52.54464781, "lonStart": 13.35313507, "latEnd": 52.54464781, "lonEnd": 13.35313507, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252736}},
 *  {"model": "draw.data", "pk": 1435, "fields": {"latStart": 52.54463462, "lonStart": 13.35312013, "latEnd": 52.54463461, "lonEnd": 13.35312012, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252737}},
 *  {"model": "draw.data", "pk": 1436, "fields": {"latStart": 52.54464781, "lonStart": 13.35313507, "latEnd": 52.5446478, "lonEnd": 13.35313506, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252737}},
 *  {"model": "draw.data", "pk": 1437, "fields": {"latStart": 52.5446478, "lonStart": 13.35313506, "latEnd": 52.5446478, "lonEnd": 13.35313506, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252738}},
 *  {"model": "draw.data", "pk": 1438, "fields": {"latStart": 52.54463461, "lonStart": 13.35312012, "latEnd": 52.5446346, "lonEnd": 13.35312012, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252738}},
 *  {"model": "draw.data", "pk": 1439, "fields": {"latStart": 52.5446346, "lonStart": 13.35312012, "latEnd": 52.54463459, "lonEnd": 13.35312011, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252739}},
 *  {"model": "draw.data", "pk": 1440, "fields": {"latStart": 52.5446478, "lonStart": 13.35313506, "latEnd": 52.54464779, "lonEnd": 13.35313505, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252739}},
 *  {"model": "draw.data", "pk": 1441, "fields": {"latStart": 52.54463459, "lonStart": 13.35312011, "latEnd": 52.54463458, "lonEnd": 13.3531201, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252740}},
 *  {"model": "draw.data", "pk": 1442, "fields": {"latStart": 52.54464779, "lonStart": 13.35313505, "latEnd": 52.54464779, "lonEnd": 13.35313505, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252740}},
 *  {"model": "draw.data", "pk": 1443, "fields": {"latStart": 52.54463458, "lonStart": 13.3531201, "latEnd": 52.54463457, "lonEnd": 13.35312009, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252741}},
 *  {"model": "draw.data", "pk": 1444, "fields": {"latStart": 52.54464779, "lonStart": 13.35313505, "latEnd": 52.54464779, "lonEnd": 13.35313504, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252741}},
 *  {"model": "draw.data", "pk": 1445, "fields": {"latStart": 52.54463457, "lonStart": 13.35312009, "latEnd": 52.54463457, "lonEnd": 13.35312008, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252742}},
 *  {"model": "draw.data", "pk": 1446,"fields": {"latStart": 52.54464779, "lonStart": 13.35313504, "latEnd": 52.54464779, "lonEnd": 13.35313504, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252742}},
 *  {"model": "draw.data", "pk": 1447, "fields": {"latStart": 52.54464779, "lonStart": 13.35313504, "latEnd": 52.54464778, "lonEnd": 13.35313503, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252743}},
 *  {"model": "draw.data", "pk": 1448, "fields": {"latStart": 52.54463457, "lonStart": 13.35312008, "latEnd": 52.54463457, "lonEnd": 13.35312007, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252743}},
 *  {"model": "draw.data", "pk": 1449, "fields": {"latStart": 52.54463457, "lonStart": 13.35312007, "latEnd": 52.54463456, "lonEnd": 13.35312006, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252744}},
 *  {"model": "draw.data", "pk": 1450, "fields": {"latStart": 52.54464778, "lonStart": 13.35313503, "latEnd": 52.54464778, "lonEnd": 13.35313503, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252744}},
 *  {"model": "draw.data", "pk": 1451, "fields": {"latStart": 52.54464778, "lonStart": 13.35313503, "latEnd": 52.54464778, "lonEnd": 13.35313502, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252745}},
 *  {"model": "draw.data", "pk": 1452, "fields": {"latStart": 52.54463456, "lonStart": 13.35312006, "latEnd": 52.54463456, "lonEnd": 13.35312005, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252745}},
 *  {"model": "draw.data", "pk": 1453, "fields": {"latStart": 52.54463456, "lonStart": 13.35312005, "latEnd": 52.54463455, "lonEnd": 13.35312004, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252746}},
 *  {"model": "draw.data", "pk": 1454, "fields": {"latStart": 52.54464778, "lonStart": 13.35313502, "latEnd": 52.54464779, "lonEnd": 13.35313501, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252746}},
 *  {"model": "draw.data", "pk": 1455, "fields": {"latStart": 52.54463455, "lonStart": 13.35312004, "latEnd": 52.54463455, "lonEnd": 13.35312003, "thickness": 25.0, "color": -8192256.0, "timestamp": 1478252747}},
 *  {"model": "draw.data", "pk": 1456, "fields": {"latStart": 52.54464779, "lonStart": 13.35313501, "latEnd": 52.54464779, "lonEnd": 13.35313501, "thickness": 3.0, "color": -8192256.0, "timestamp": 1478252747}}
 *  ]}

 */

public class Result {

    private ArrayList<Line> result;

    public ArrayList<Line> getResult() {
        return result;
    }
}
