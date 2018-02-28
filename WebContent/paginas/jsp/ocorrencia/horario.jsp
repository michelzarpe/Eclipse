<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<label for="horEnt1" class="preField">Horário</label>
das
<input id="q1" type="text" name="ocorrencia.horEnt1" class="medium" value="" dojoType="dijit.form.TimeTextBox"
	constraints="{timePattern:'HH:mm'}" required="true"
	invalidMessage="Hora inválida. Use HH:mm onde HH é de 00 - 23 horas." style="width: 50px;">
às
<input id="q2" type="text" name="ocorrencia.horSai1" class="medium" value="" dojoType="dijit.form.TimeTextBox"
	constraints="{timePattern:'HH:mm'}" required="true"
	invalidMessage="Hora inválida. Use HH:mm onde HH é de 00 - 23 horas." style="width: 50px;">
<br/>
<label for="horEnt1" class="preField">após intervalo</label>
das
<input id="q3" type="text" name="ocorrencia.horEnt2" class="medium" value="" dojoType="dijit.form.TimeTextBox"
	constraints="{timePattern:'HH:mm'}" required="true"
	invalidMessage="Hora inválida. Use HH:mm onde HH é de 00 - 23 horas." style="width: 50px;">
às
<input id="q4" type="text" name="ocorrencia.horSai2" class="medium" value="" dojoType="dijit.form.TimeTextBox"
	constraints="{timePattern:'HH:mm'}" required="true"
	invalidMessage="Hora inválida. Use HH:mm onde HH é de 00 - 23 horas." style="width: 50px;"><br/>