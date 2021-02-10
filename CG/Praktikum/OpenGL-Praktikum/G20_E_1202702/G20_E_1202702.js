
/*****
/*
/* Beispielprogramm für die Lehrveranstaltung Computergraphik
/* HS RheinMain
/* Prof. Dr. Ralf Dörner
/*
/* basierend auf einem Programm von Edward Angel
/* http://www.cs.unm.edu/~angel/WebGL/
/*
/****/


/***   Deklaration globaler Variablen */

// Referenz auf Bereich, in den gezeichnet wird
var canvas;

// Referenz auf WebGL-Kontext, über die OpenGL Befehle ausgeführt werden
var gl;

// Referenz auf die Shaderprogramme
var program;

// Matrix für die Umrechnung Objektkoordinaten -> Weltkoordinaten
var model;

// Matrix für die Umrechnung Weltkoordinaten -> Kamerakoordinaten
var view; 

// Matrix für die Umrechnung Kamerakoordinaten -> Clippingkoordinaten
var projection;

// Matrix für die Umrechnung von Normalen aus Objektkoordinaten -> Viewkoordinaten
var normalMat;

// Flag, das angibt, ob eine Beleuchtungsrechnung durchgeführt wird (true)
// oder ob einfach die übergebenen Eckpunktfarben übernommen werden (false)
var lighting = true;

// Anzahl der Eckpunkte der zu zeichenden Objekte 
var numVertices  = 0;

// Array, in dem die Koordinaten der Eckpunkte der zu zeichnenden Objekte eingetragen werden
var vertices = [];

// Array, in dem die Farben der Eckpunkte der zu zeichnenden Objekte eingetragen werden
var colors = [];

// Array, in dem die Eckpunktkoordinaten der zu zeichnenden Objekte eingetragen werden
var pointsArray = [];

// Array, in dem die Normale je Eckpunkt der zu zeichnenden Objekte eingetragen werden
var normalsArray = [];

// Array, in dem die Farbwerte je Eckpunkt der zu zeichnenden Objekte eingetragen werden
var colorsArray = [];

// Variablen für die Drehung des Würfels
var axis = 0;
var theta =[0, 0, 0, 0];

// Variablen, um die Anzahl der Frames pro Sekunde zu ermitteln
var then = Date.now() / 1000;
var counter = 0;

// OpenGL-Speicherobjekt f�r Farben
var nBuffer;

// OpenGL-Speicherobjekt f�r Vertices
var vBuffer;

// OpenGL-Speicherobjekt f�r Normalen
var nBuffer;

// OpenGL-Speicherobjekt für TexCoord
var tcBuffer;

//meine Variablen
var running = true;
var prevAxis = 0;
var turnMe = 2.0;
var fps_precision = 2;
var würfelTheta = 0;
var specularColor =  vec4(1,1,1,1);
var ambientIntensity = 0.5;
var shininess = 100;
var textur = document.getElementById("texImage");

// Cartoon Presets
var cartoonOn = true;
var cartoonLight = .7;
var cartoonDark = .4;

var red = vec4(1.0, 0.0, 0.0, 1.0);
var ylw = vec4(1.0, 1.0, 0.0, 1.0);
var grn = vec4(0.0, 1.0, 0.0, 1.0);
var blu = vec4(0.0, 0.0, 1.0, 1.0);
var pur = vec4(1.0, 0.0, 1.0, 1.0);
var tea = vec4(0.0, 1.0, 1.0, 1.0);
var wht = vec4(1.0, 1.0, 1.0, 1.0);
var blk = vec4(0.0, 0.0, 0.0, 1.0);


var defaultColor_Cube = [
    red, 
    ylw,
    grn,
    tea,
    blu,
    pur,
    red,
    ylw
];

var redBlack_Cube = [
    grn, 
    blk,
    grn,
    grn,
    blk,
    grn,
    grn,
    blk
];

var image = document.getElementById("texImage");

/*** Hilfsfunktionen zum Zeichnen von Objekten */


//
// Funktion, die ein Quadrat in das pointsArray, colorsArray und normalsArray einträgt
// Das Quadrat wird dabei in zwei Dreiecke trianguliert, da OpenGL keine Vierecke 
// nativ zeichnen kann.
//
// Übergeben werden für Indices auf die vier Eckpunkte des Vierecks
//

function quad(a, b, c, d) {

     // zunächst wird die Normale des Vierecks berechnet. t1 ist der Vektor von Eckpunkt a zu Eckpunkt b
     // t2 ist der Vektor von Eckpunkt von Eckpunkt a zu Eckpunkt c. Die Normale ist dann das 
     // Kreuzprodukt von t1 und t2
     var t1 = subtract(vertices[b], vertices[a]);
     var t2 = subtract(vertices[c], vertices[a]);
     var normal = cross(t1, t2);
     normal = vec3(normal);

     // und hier kommt die Eintragung der Infos für jeden Eckpunkt (Koordinaten, Normale, Farbe) in die globalen Arrays
     // allen Eckpunkten wird die gleiche Farbe zugeordnet, dabei 
    
     // erstes Dreieck
     pointsArray.push(vertices[a]); 
     normalsArray.push(normal);
	   colorsArray.push(colors[a]);
    
     pointsArray.push(vertices[b]); 
     normalsArray.push(normal);
	   colorsArray.push(colors[a]);
    
     pointsArray.push(vertices[c]); 
     normalsArray.push(normal);
	   colorsArray.push(colors[a]);
     
     // zweites Dreieck
     pointsArray.push(vertices[a]);  
     normalsArray.push(normal); 
	   colorsArray.push(colors[a]);
     
     pointsArray.push(vertices[c]); 
     normalsArray.push(normal); 
	   colorsArray.push(colors[a]);
     
     pointsArray.push(vertices[d]); 
     normalsArray.push(normal);
	   colorsArray.push(colors[a]);
	
     // durch die beiden Dreiecke wurden 6 Eckpunkte in die Array eingetragen
     numVertices += 6;    
}

function tri(a, b, c) {

    // zunächst wird die Normale des Vierecks berechnet. t1 ist der Vektor von Eckpunkt a zu Eckpunkt b
    // t2 ist der Vektor von Eckpunkt von Eckpunkt a zu Eckpunkt c. Die Normale ist dann das 
    // Kreuzprodukt von t1 und t2
    var t1 = subtract(vertices[b], vertices[a]);
    var t2 = subtract(vertices[c], vertices[a]);
    var normal = cross(t1, t2);
    normal = vec3(normal);

    // und hier kommt die Eintragung der Infos für jeden Eckpunkt (Koordinaten, Normale, Farbe) in die globalen Arrays
    // allen Eckpunkten wird die gleiche Farbe zugeordnet, dabei 
   
    // erstes Dreieck
    pointsArray.push(vertices[a]); 
    normalsArray.push(normal);
      colorsArray.push(colors[a]);
   
    pointsArray.push(vertices[b]); 
    normalsArray.push(normal);
      colorsArray.push(colors[a]);
   
    pointsArray.push(vertices[c]); 
    normalsArray.push(normal);
      colorsArray.push(colors[a]);
    
    // durch die beiden Dreiecke wurden 6 Eckpunkte in die Array eingetragen
    numVertices += 3;    
}

function colorMatrix(color, edges){
    var result = [];
    var i;
    for(i=0; i<edges; i++){
        result.push(color);
    }

    return result;
}

//
// Funktion, die einen Würfel zeichnet (Mittelpunkt liegt im Ursprung, Kantenlänge beträgt 1)
//

function drawCube(color)
{

    // zunächst werden die Koordinaten der 8 Eckpunkte des Würfels definiert
    vertices = [
        vec4( -0.5, -0.5,  0.5, 1.0 ), // 0
        vec4( -0.5,  0.5,  0.5, 1.0 ), // 1
        vec4( 0.5,  0.5,  0.5, 1.0 ),  // 2 
        vec4( 0.5, -0.5,  0.5, 1.0 ),  // 3
        vec4( -0.5, -0.5, -0.5, 1.0 ), // 4
        vec4( -0.5,  0.5, -0.5, 1.0 ), // 5
        vec4( 0.5,  0.5, -0.5, 1.0 ),  // 6
        vec4( 0.5, -0.5, -0.5, 1.0 )   // 7
    ];

    // hier werden verschiedene Farben definiert (je eine pro Eckpunkt)
    switch(color){
        case "standard":
            colors = defaultColor_Cube;
            break;
        case "redBlack":
            colors = redBlack_Cube;
            break;
        default:
            colors = colorMatrix(color, 8);
            
    }

    // und hier werden die Daten der 6 Seiten des Würfels in die globalen Arrays eingetragen
    // jede Würfelseite erhält eine andere Farbe
    quad( 1, 0, 3, 2 );
    quad( 2, 3, 7, 6 );
    quad( 3, 0, 4, 7 );
    quad( 6, 5, 1, 2 );
    quad( 4, 5, 6, 7 );
    quad( 5, 4, 0, 1 );
    
    
    // die eingetragenen Werte werden an den Shader übergeben
    
        gl.bindBuffer( gl.ARRAY_BUFFER, nBuffer );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(normalsArray), gl.STATIC_DRAW );
    
    var vNormal = gl.getAttribLocation( program, "vNormale" );
	gl.vertexAttribPointer( vNormal, 3, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( vNormal );

    gl.bindBuffer( gl.ARRAY_BUFFER, vBuffer );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(pointsArray), gl.STATIC_DRAW );
    
    var vPosition = gl.getAttribLocation(program, "vPosition");
    gl.vertexAttribPointer(vPosition, 4, gl.FLOAT, false, 0, 0);
    gl.enableVertexAttribArray(vPosition);
    
    gl.bindBuffer( gl.ARRAY_BUFFER, cBuffer );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(colorsArray), gl.STATIC_DRAW );
    
    var cPosition = gl.getAttribLocation(program, "vColor");
    gl.vertexAttribPointer(cPosition, 4, gl.FLOAT, false, 0, 0);
    gl.enableVertexAttribArray(cPosition);
    
}

function drawPyramid(color)
{

    // zunächst werden die Koordinaten der Eckpunkte definiert
    vertices = [
        vec4( -2, 0, -1, 1.0 ), // 0
        vec4(  2, 0, -1, 1.0 ), // 1
        vec4( -2, 0,  1, 1.0 ), // 2
        vec4(  2, 0,  1, 1.0 ), // 3
        vec4(  0, 4,  0, 1.0 ) //4
    ];

    colors = colorMatrix(ylw, 6);

    
    // hier werden verschiedene Farben definiert (je eine pro Eckpunkt)
    // colors = [
    //     vec4(1.0, 0.0, 0.0, 1.0), 
	//     vec4(1.0, 1.0, 0.0, 1.0),
    //     vec4(0.0, 1.0, 0.0, 1.0),
    //     vec4(0.0, 1.0, 1.0, 1.0),
	//     vec4(0.0, 0.0, 1.0, 1.0),
    // ];

    tri( 0, 4, 1);
    tri( 1, 4, 3);
    tri( 2, 3, 4);
    tri( 0, 2, 4);
    quad( 1, 3, 2, 0);

    
    
    // die eingetragenen Werte werden an den Shader übergeben
    
        gl.bindBuffer( gl.ARRAY_BUFFER, nBuffer );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(normalsArray), gl.STATIC_DRAW );
    
    var vNormal = gl.getAttribLocation( program, "vNormale" );
	  gl.vertexAttribPointer( vNormal, 3, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( vNormal );

    gl.bindBuffer( gl.ARRAY_BUFFER, vBuffer );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(pointsArray), gl.STATIC_DRAW );
    
    var vPosition = gl.getAttribLocation(program, "vPosition");
    gl.vertexAttribPointer(vPosition, 4, gl.FLOAT, false, 0, 0);
    gl.enableVertexAttribArray(vPosition);
    
    gl.bindBuffer( gl.ARRAY_BUFFER, cBuffer );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(colorsArray), gl.STATIC_DRAW );
    
    var cPosition = gl.getAttribLocation(program, "vColor");
    gl.vertexAttribPointer(cPosition, 4, gl.FLOAT, false, 0, 0);
    gl.enableVertexAttribArray(cPosition);
    
}


/*** Funktionen zum Aufbau der Szene */

//
// Funktion zum setzen der inneren und äußeren Parameter der Kamera
//

function setCamera()
{
  
    // es wird ermittelt, welches Element aus der Kameraauswahlliste aktiv ist
    var camIndex = document.getElementById("Cameralist").selectedIndex;

    // Punkt, an dem die Kamera steht  
    var eye;
  
    // Punkt, auf den die Kamera schaut
    var vrp;
  
    // Vektor, der nach oben zeigt  
    var upv;
	
    switch(camIndex){
        case 0:
            eye = vec3(12.0,12.0,4.0);
     		vrp = vec3(0.0,0.0,0.0);
            upv = vec3(0.0,1.0,0.0);
            break;
        case 1:
            eye = vec3(10,0,0);
     		vrp = vec3(0.0,0.0,0.0);
            upv = vec3(0.0,1.0,0.0);
            break;
        case 2:
            eye = vec3(0,10,0);
     		vrp = vec3(0.0,0.0,0.0);
            upv = vec3(1.0,0.0,0.0);
            break;
        case 3:
            eye = vec3(0,0,10);
     		vrp = vec3(0.0,0.0,0.0);
            upv = vec3(0.0,1.0,0.0);
            break;
        case 4:
            eye = vec3(12,12,4);
     		vrp = vec3(2.0,4.0,1.0);
            upv = vec3(0.0,1.0,0.0);
            break;
        default:
            console.log("no such camera implmeneted");    
	};

    // hier wird die Viewmatrix unter Verwendung einer Hilfsfunktion berechnet,
    // die in einem externen Javascript (MV.js) definiert wird
    view = lookAt(eye, vrp, upv);  
    
    // die errechnete Viewmatrix wird an die Shader übergeben
    // die Funktion flatten löst dabei die eigentlichen Daten aus dem Javascript-Array-Objekt
    gl.uniformMatrix4fv( gl.getUniformLocation(program, "mView"), false, flatten(view) );

    // nachdem die inneren Parameter gesetzt wurden, werden nun die äußeren Parameter gesetzt
    // dazu wird die Projektionmatrix mit einer Hilfsfunktion aus einem externen Javascript (MV.js)
    // definiert
    // der Field-of-View wird auf 60° gesetzt, das Seitenverhältnis ist 1:1 (d.h. das Bild ist quadratisch),
    // die near-Plane hat den Abstand 0.01 von der Kamera und die far-Plane den Abstand 100
    projection = perspective(60.0, 1.0, 0.01, 100.0);
    /*
    d) Winkel -> 30° :
        Es zoomt ein bisschen heran, da jetzt weniger im Bild dargestellt werden kann, da der \n
        Sichwinkel kleiner ist.
    
    e) Near-Plane -> 15 :
        Die Szene ist vorne "abgeschnitten", weil erst Dinge ab einem Abstand von 15 vom \n
        Betrachter gerendert werden.
    
    f) Aspect-Ratio -> 16:9 :
        Das Bild wirkt seitlich gestaucht, da es für ein 16:9 Verhältnis berechnet wird, aber \n
        auf einem quadratischen canvas dargestellt wird.

        aus dem html  ->    [...]  <canvas id="gl-canvas" width="600" height="600">  [...]

    */
    
    // die errechnete Viewmatrix wird an die Shader übergeben
    gl.uniformMatrix4fv( gl.getUniformLocation(program, "mProjection"), false, flatten(projection));
    
}


//
// die Funktion spezifiziert die Lichtquellen, führt schon einen Teil der Beleuchtungsrechnung durch
// und übergibt die Werte an den Shader
// 
// der Parameter materialDiffuse ist ein vec4 und gibt die Materialfarbe für die diffuse Reflektion an
//

function calculateLights( material )
{
    // zunächst werden die Lichtquellen spezifiziert (bei uns gibt es eine Punktlichtquelle)
    
    // die Position der Lichtquelle (in Weltkoordinaten)
    var lightPosition = vec4(7.0, 7.0, 0.0, 1.0 );

	// die Farbe des AmbientLights
	var ambientLight = scale(ambientIntensity ,vec4(1,1,1,1));
    
    // die Farbe der Lichtquelle im diffusen Licht
    var diffuseLight = vec4( 1.0, 1.0, 1.0, 1.0 );

    // dann wird schon ein Teil der Beleuchtungsrechnung ausgeführt - das könnte man auch im Shader machen
    // aber dort würde diese Rechnung für jeden Eckpunkt (unnötigerweise) wiederholt werden. Hier rechnen wir
    // das Produkt aus diffuseLight und materialDiffuse einmal aus und übergeben das Resultat. Zur Multiplikation
    // der beiden Vektoren nutzen wir die Funktion mult aus einem externen Javascript (MV.js)
    
    var diffuseProduct = mult(diffuseLight, material.materialDiffuseColor);

	var ambientProduct = scale( material.materialAmbientIntensity, ambientLight);

	var specularProduct = mult(diffuseLight, material.materialSpecularColor);

	// die Werte für die Beleuchtungsrechnung werden an die Shader übergeben
    
    // Übergabe der Position der Lichtquelle
    // flatten ist eine Hilfsfunktion, welche die Daten aus dem Javascript - Objekt herauslöst
    gl.uniform4fv(gl.getUniformLocation(program, "lightPosition"), flatten(lightPosition) );
    // Übergabe des ambientProduct
	gl.uniform4fv(gl.getUniformLocation(program, "ambientProduct"), flatten(ambientProduct));
	// Übergabe des specularProduct
	gl.uniform4fv(gl.getUniformLocation(program, "specularProduct"), flatten(specularProduct));
	// Übergabe der shininess
    gl.uniform1f(gl.getUniformLocation(program, "shininess"), material.materialShininess);
    // Übergabe des diffuseProduct
    gl.uniform4fv(gl.getUniformLocation(program, "diffuseProduct"), flatten(diffuseProduct) );
       
}


//
// Die Funktion setzt die Szene zusammen, dort wird ein Objekt nach dem anderen gezeichnet
// 

function displayScene(){ 
    //
    // Die Kamera für das Bild wird gesetzt
    
    // View-Matrix und Projection-Matrix zur Kamera berechnen
    setCamera();
    
            
    
    buildObject(
        "cube",                 //type
        false,                   //lighting-Berechnung
        "redBlack",                    //farbe
        [5,0,1],                //translation
        [würfelTheta*2,0,0,1],    //rotation
        [0,1,0,0],              //static Rotation
        [1,1,1],             //skalierung
        false,                  //doCartoon
        0);                  //textureBlend

    buildObject(
        "cube",                 //type
        true,                   //lighting-Berechnung
        blu,                    //farbe
        [5,0,-3],                //translation
        [würfelTheta,0,1,1],    //rotation
        [0,1,0,0],              //static Rotation
        [2,2,2],              //skalierung
        false,
        0.7); 


    buildObject(
        "pyramid",                 //type
        true,                   //lighting-Berechnung
        ylw,                    //farbe
        [2,0,1],                //translation
        [0,1,0,0],              //rotation
        [0,1,0,0],              //static Rotation
        [1,1,1]);               //skalierung

    buildObject(
        "pyramid",              //type
        true,                   //lighting-Berechnung
        red,                    //farbe
        [2,8,1],                //translation
        [0,1,0,0],              //rotation
        [180,1,0,0],            //static Rotation
        [1,1,1]);               //skalierung

    var rotationAngle = 90 + 1/4 * 180/Math.PI;
    buildObject(
        "pyramid",              //type
        true,                   //lighting Berechnung
        blu,                    //farbe
        [2,6,1.5],              //translation
        [0,1,0,0],              //rotation
        [rotationAngle,1,0,0],  //static Rotation
        [0.4,0.4,0.4]);         //skalierung                
        
    buildObject(
        "teapot",               //type
        true,                   //lighting Berechnung
        blu,                    //farbe
        [-5,0,6],               //translation
        [würfelTheta,0,1,0],    //rotation
        [0,1,0,0],              //static Rotation
        [0.3,0.3,0.3],          //skalierung
        cartoonOn);             //doCartoon       

    return;
    // zunächst werden die Daten für die globalen Arrays gelöscht
    // dies ist auch schon beim ersten Objekt zu tun, denn aus den
    // Berechnungen eines früheren Frames könnten hier schon Werte in den Arrays stehen
    // auch die Anzahl der Eckpunkte des zu zeichnenden Objekts wird auf 0 zurückgesetzt

} // Ende der Funktion displayScene()


//
// hier wird eine namenslose Funktion definiert, die durch die Variable render zugegriffen werden kann.
// diese Funktion wird für jeden Frame aufgerufen
//

function buildObject(
    shape,
    lighting,
    color, 
    translation,
    ownRotation = [0,1,0,0],
    staticRotation = [0,1,0,0],
    scale = [1,1,1],
    doCartoon = false,
    textureBlend = 0
    ){


    
    numVertices = 0;
	pointsArray.length=0;
	colorsArray.length=0;
	normalsArray.length=0;
    


    if(doCartoon){
        cartoonLight = .7;
        cartoonDark = .4;
    }
    else{
        cartoonDark = -1;
        cartoonLight = -1;
    }
    
    // jetzt werden die Arrays mit der entsprechenden Zeichenfunktion mit Daten gefüllt
    switch(shape){
        case"cube":
            drawCube(color);
            break;
        case"pyramid":
            drawPyramid(color);
            break
        case"teapot":
            drawTeapot();
            break;
        default:
            console.log("unkown shape "+shape);
    }
    
    // es wird festgelegt, ob eine Beleuchtungsrechnung für das Objekt durchgeführt wird oder nicht
    //var lighting = true; // Beleuchtungsrechnung wird durchgeführt
    
    // die Information über die Beleuchtungsrechnung wird an die Shader weitergegeben
    gl.uniform1i(gl.getUniformLocation(program, "lighting"),lighting);
    
    
    if (lighting) {

        gl.uniform1f(gl.getUniformLocation(program, "blendMe"), textureBlend);
        // es soll also eine Beleuchtungsrechnung durchgeführt werden
        
        // die Materialfarbe für diffuse Reflektion wird spezifiziert
        var material = {
            materialDiffuseColor: color,
            materialSpecularColor: color,
            materialAmbientIntensity: .5,
            materialShininess: shininess
        };
    
        // die Beleuchtung wird durchgeführt und das Ergebnis an den Shader übergeben
        calculateLights( material );
        
    } 

    gl.uniform1f(gl.getUniformLocation(program, "cartoonLight"), cartoonLight);
	gl.uniform1f(gl.getUniformLocation(program, "cartoonDark"), cartoonDark);
    
    
    // es muss noch festgelegt werden, wo das Objekt sich in Weltkoordinaten befindet,
    // d.h. die Model-Matrix muss errechnet werden. Dazu werden wieder Hilfsfunktionen
    // für die Matrizenrechnung aus dem externen Javascript MV.js verwendet
   
    // Initialisierung mit der Einheitsmatrix 
	model = mat4();    
   
    //Weltrotation
    // Das Objekt wird am Ende noch um die x-Achse rotiert 
    model = mult(model, rotate(theta[1], [1, 0, 0] ));
    
    // Zuvor wird das Objekt um die y-Achse rotiert
    model = mult(model, rotate(theta[2], [0, 1, 0] ));
    
    // Als erstes wird das Objekt um die z-Achse rotiert 
    model = mult(model, rotate(theta[3], [0, 0, 1] ));

    //translation
    model = mult(model, translate(translation[0], translation[1], translation[2]));
   
    //eigene rotation
    model = mult(model, rotate(ownRotation[0], [ownRotation[1], ownRotation[2], ownRotation[3]]));

    //static rotation
    model = mult(model, rotate(staticRotation[0], [staticRotation[1], staticRotation[2], staticRotation[3]]));
	
    //skalierung
    model = mult(model, scalem(scale[0], scale[1], scale[2]));

    // die Model-Matrix ist fertig berechnet und wird an die Shader übergeben 
 	gl.uniformMatrix4fv( gl.getUniformLocation(program, "mModel"), false, flatten(model) );
    
    // jetzt wird noch die Matrix errechnet, welche die Normalen transformiert
    normalMat = mat4();
    normalMat = mult( view, model );
    normalMat = inverse( normalMat );
    normalMat = transpose( normalMat );
    
    // die Normal-Matrix ist fertig berechnet und wird an die Shader übergeben 
 	gl.uniformMatrix4fv( gl.getUniformLocation(program, "mNormale"), false, flatten(normalMat) );


     if (shape == "teapot") {
		//drawElements statt drawArrays
		gl.drawElements(gl.TRIANGLES, teapotVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
		return;
	}
    // schließlich wird alles gezeichnet. Dabei wird der Vertex-Shader numVertices mal aufgerufen
    // und dabei die jeweiligen attribute - Variablen für jeden einzelnen Vertex gesetzt
    // außerdem wird OpenGL mitgeteilt, dass immer drei Vertices zu einem Dreieck im Rasterisierungsschritt
    // zusammengesetzt werden sollen
    gl.drawArrays( gl.TRIANGLES, 0, numVertices );
}

var render = function(){
    counter = counter + 1;
    fps = counter/fps_precision;
    //console.log(fps);
    
    if(Date.now() - then >= fps_precision*1000){
        document.getElementById("fps").innerHTML = fps;
        counter =0;
        then=Date.now();
    }
    // den Framebuffer (hier wird das Bild hineingeschrieben) und den z-Buffer (wird für Verdeckungsrechnung benötigt)
    // initialisieren.
    
    gl.clear( gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
    
    
    // Durchführung der Animation: der Würfel wird um 2° weiter gedreht und zwar um die aktuell ausgewählte Achse
    theta[axis] += turnMe;
    würfelTheta += 0.5;
            
    // jetzt kann die Szene gezeichnet werden
    displayScene();
    
    // der Frame fertig gezeichnet ist, wird veranlasst, dass der nächste Frame gezeichnet wird. Dazu wird wieder
    // die die Funktion aufgerufen, welche durch die Variable render spezifiziert wird
    requestAnimFrame(render);
}

/*** Funktionen zur Ausführung von WebGL  */


//
// Diese Funktion wird beim Laden der HTML-Seite ausgeführt. Sie ist so etwas wie die "main"-Funktion
// Ziel ist es, WebGL zu initialisieren
//

window.onload = function init() {
    
    // die Referenz auf die Canvas, d.h. den Teil des Browserfensters, in den WebGL zeichnet, 
    // wird ermittelt (über den Bezeichner in der HTML-Seite)
    canvas = document.getElementById( "gl-canvas" );
    
    // über die Canvas kann man sich den WebGL-Kontext ermitteln, über den dann die OpenGL-Befehle
    // ausgeführt werden
    gl = WebGLUtils.setupWebGL( canvas );
    if ( !gl ) { alert( "WebGL isn't available" ); }

    // allgemeine Einstellungen für den Viewport (wo genau das Bild in der Canvas zu sehen ist und
    // wie groß das Bild ist)
    gl.viewport( 0, 0, canvas.width, canvas.height );
  
    // die Hintergrundfarbe wird festgelegt
    gl.clearColor( 0.9, 0.9, 1.0, 1.0 );
    
    // die Verdeckungsrechnung wird eingeschaltet: Objekte, die näher an der Kamera sind verdecken
    // Objekte, die weiter von der Kamera entfernt sind
    gl.enable(gl.DEPTH_TEST);

    // der Vertex-Shader und der Fragment-Shader werden initialisiert
    program = initShaders( gl, "vertex-shader", "fragment-shader" );
    
    gl.useProgram(program);

	// OpenGL Speicherobjekte anlegen
	vBuffer = gl.createBuffer();
	nBuffer = gl.createBuffer();
	cBuffer = gl.createBuffer();
	tcBuffer = gl.createBuffer();
	gl.bindBuffer(gl.ARRAY_BUFFER, tcBuffer);
	// Set Texcoords.
	setTexcoords(gl);
	// Textur laden
	var myImage = document.getElementById("texImage");
	var textur = gl.createTexture();
	gl.bindTexture(gl.TEXTURE_2D, textur);
	gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, true);
	gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGB, gl.RGB, gl.UNSIGNED_BYTE, myImage);
	gl.generateMipmap(gl.TEXTURE_2D);
	gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST_MIPMAP_LINEAR);
	gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST);
	gl.uniform1i(gl.getUniformLocation(program, "textur"), 0);

	var texCoordLoc = gl.getAttribLocation(program, "a_texcoord");
	// Turn on the texcoord attribute
	gl.enableVertexAttribArray(texCoordLoc);
	// bind the texcoord buffer.
	gl.bindBuffer(gl.ARRAY_BUFFER, tcBuffer);
	gl.vertexAttribPointer(texCoordLoc, 2, gl.FLOAT, false, 0, 0);

    
	// Teapot laden
    loadTeapot();

    // die Callbacks für das Anklicken der Buttons wird festgelegt
    // je nachdem, ob man den x-Achsen, y-Achsen oder z-Achsen-Button klickt, hat
    // axis einen anderen Wert
    document.getElementById("ButtonX").onclick = function(){
        axis = 1;
        turnMe=2.0;
    };
    document.getElementById("ButtonY").onclick = function(){
        axis = 2;
        turnMe=2.0;
    };
    document.getElementById("ButtonZ").onclick = function(){
        console.log("Start z rotation\n")
        axis = 3;
        turnMe=2.0;
    };
    document.getElementById("ButtonT").onclick = function(){
        if(running){
            prevAxis = axis;
            axis=0;
            turnMe=0;
            running=false;
        }
        else{
            axis=prevAxis;
            turnMe=2.0;
            running=true;
        };
    };
    document.getElementById("ButtonCartoon").onclick = function(){
        cartoonOn = !cartoonOn;
    };
    
    
		// jetzt kann mit dem Rendern der Szene begonnen werden  
    render();
}

function setTexcoords(gl) {
	gl.bufferData(
		gl.ARRAY_BUFFER,
		new Float32Array([
			0, 0,
			0, 2,
			2, 2,
			0, 0,
			2, 2,
			2, 0,

			0, 0,
			0, 2,
			2, 2,
			0, 0,
			2, 2,
			2, 0,

			0, 0,
			0, 2,
			2, 2,
			0, 0,
			2, 2,
			2, 0,

			0, 0,
			0, 2,
			2, 2,
			0, 0,
			2, 2,
			2, 0,

			0, 0,
			0, 2,
			2, 2,
			0, 0,
			2, 2,
			2, 0,

			0, 0,
			0, 2,
			2, 2,
			0, 0,
			2, 2,
			2, 0,
		]),
		gl.STATIC_DRAW
	);
}