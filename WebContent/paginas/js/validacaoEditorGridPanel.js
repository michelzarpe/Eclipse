<script type="text/javascript">
Ext.grid.ColumnModelEdit = function(config){
	var columns = config.columns || config;
	Ext.each(columns, function(column) {
		if (column.editor) {
			Ext.apply(column, {
				renderer: function(value, metadata, record, rowIndex, colIndex, store) {
					if (record._valid && (record._valid[colIndex] === false)) {
						metadata.css = 'x-grid3-invalid-cell';
					}
					return value;
				},
				editor: new Ext.grid.GridEditor(Ext.apply(column.editor, {
					validateValue: function(v) {
						this.record._valid[this.col] = Ext.form.TextField.prototype.validateValue.call(this, v);
						return true;
					}
				}), {
					listeners: {
						beforestartedit: function(e) {
							if (!e.record._valid) {
							   e.record._valid = [];
						   }
						   e.field.record = e.record;
						   e.field.col = e.col;
					   }
				   }
				})
			});
		}
	});
	Ext.grid.ColumnModelEdit.superclass.constructor.call(this, config);
}
Ext.extend(Ext.grid.ColumnModelEdit, Ext.grid.ColumnModel);

Ext.onReady(function(){
	new Ext.Viewport({
		layout: 'fit',
		items: [{
			id: 'grid',
			xtype: 'editorgrid',
			store: new Ext.data.SimpleStore({
				fields: ['text'],
				data: ['ABC', 'DEF', 'GHI'],
				expandData: true
			}),
			cm: new Ext.grid.ColumnModelEdit([{
				header: 'Text',
				dataIndex: 'text',
				editor: new Ext.form.TextField({
					allowBlank: false,
					maxLength: 3
				})
			}])
		}]
	});
});
</script>
<style type="text/css">
.x-grid3-invalid-cell {
	background: transparent url(resources/images/default/grid/invalid_line.gif) repeat-x bottom;
}
</style>