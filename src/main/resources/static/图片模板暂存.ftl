<w:p wsp:rsidR="00000000" wsp:rsidRDefault="009D5F70">
    <w:pPr>
        <w:spacing w:line="240" w:line-rule="auto"/>
        <w:rPr>
            <w:rFonts w:hint="fareast"/>
        </w:rPr>
    </w:pPr>
    <w:r>
        <w:rPr>
            <w:rFonts w:hint="fareast"/>
        </w:rPr>
        <w:pict>
            <v:shapetype id="_x0000_t75" coordsize="21600,21600" o:spt="75"
                         o:preferrelative="t" path="m@4@5l@4@11@9@11@9@5xe" filled="f"
                         stroked="f">
                <v:stroke joinstyle="miter"/>
                <v:formulas>
                    <v:f eqn="if lineDrawn pixelLineWidth 0"/>
                    <v:f eqn="sum @0 1 0"/>
                    <v:f eqn="sum 0 0 @1"/>
                    <v:f eqn="prod @2 1 2"/>
                    <v:f eqn="prod @3 21600 pixelWidth"/>
                    <v:f eqn="prod @3 21600 pixelHeight"/>
                    <v:f eqn="sum @0 0 1"/>
                    <v:f eqn="prod @6 1 2"/>
                    <v:f eqn="prod @7 21600 pixelWidth"/>
                    <v:f eqn="sum @8 21600 0"/>
                    <v:f eqn="prod @7 21600 pixelHeight"/>
                    <v:f eqn="sum @10 21600 0"/>
                </v:formulas>
                <v:path o:extrusionok="f" gradientshapeok="t"
                        o:connecttype="rect"/>
                <o:lock v:ext="edit" aspectratio="t"/>
            </v:shapetype>
            <v:shape id="图片 6" o:spid="_x0000_i1025" type="#_x0000_t75"
                     alt="a"
                     style="width:${question.width}; height:${question.height };">
                <v:fill o:detectmouseclick="t"/>
                <w:binData w:name="wordml://02000001${question_index+1 }.jpg"
                           xml:space="preserve">${question.question_image}</w:binData>
                <v:imagedata src="wordml://02000001${question_index+1 }.jpg"
                             o:title="a"/>
            </v:shape>
        </w:pict>
    </w:r>
</w:p>