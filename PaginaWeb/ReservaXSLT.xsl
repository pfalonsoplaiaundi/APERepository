<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Reservas</title>
            </head>
            <body>
                <table border="1">
                    <tr>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Tipo de Habitación</th>
                        <th>Fecha de Inicio</th>
                        <th>Fecha de Fin</th>
                    </tr>
                    <!-- Recorremos cada reserva -->
                    <xsl:for-each select="reservas/reserva">
                        <tr>
                            <td><xsl:value-of select="codigo"/></td>
                            <td><xsl:value-of select="nombre"/></td>
                            <td><xsl:value-of select="apellido"/></td>
                            <td><xsl:value-of select="tipo_habitacion"/></td>
                            <td><xsl:value-of select="fecha_inicio"/></td>
                            <td><xsl:value-of select="fecha_fin"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
