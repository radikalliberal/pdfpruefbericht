# pdfpruefbericht
Dieses Programm erstellt aus einer XML-Datei (Generiert vom Programm EMC-Program) einen Prüfbericht im Pdf-Format.
Die XML-Struktur ist wie folgt:

<?xml version=" 1.0 " encoding="UTF ? 8" standalone="no" ?>
  <Report date=" " name=" " obnr=" " pruefer=" ">
    <Test humidity=" " id=" " name=" " temp=" ">
      <Setup>
        <Comment></Comment>
        <img label=" " path=" " />
        <device lastcal=" " name=" " serial=" " />
      </ Setup>
      <Segment name=" " time=" ">
        <parameter name=" " value0=" " value1=" " . . . . / >
        <parameter name=" " value0=" " value1=" " . . . . / >
        <Comment></Comment>
        <img label=" " path=" " />
        <log></ log>
      </Segment>
    </ Test>
  </ Report>
