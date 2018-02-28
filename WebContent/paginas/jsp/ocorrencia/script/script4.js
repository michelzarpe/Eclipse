function demo(d) {
	var demo = d.value;
	switch (demo) {
	case 'Opacity':
		new Effect.Opacity($('demonstracao'), {
			duration :5.0,
			from :1.0,
			to :0.0
		});
		break;
	case 'Scale':
		new Effect.Scale($('demonstracao'), 10, {
			scaleX :true,
			scaleY :true,
			scaleContent :true,
			scaleFromCenter :true,
			scaleMode :'box'
		});
		break;
	case 'MoveBy':
		new Effect.MoveBy($('demonstracao'), 0, 25);
		break;
	case 'Appear':
		new Effect.Appear($('demonstracao'));
		break;
	case 'Fade':
		new Effect.Fade($('demonstracao'));
		break;
	case 'Puff':
		new Effect.Puff($('demonstracao'));
		break;
	case 'BlindDown':
		new Effect.BlindDown($('demonstracao'));
		break;
	case 'BlindUp':
		new Effect.BlindUp($('demonstracao'));
		break;
	case 'SwitchOff':
		new Effect.SwitchOff($('demonstracao'));
		break;
	case 'SlideDown':
		new Effect.SlideDown($('demonstracao'));
		break;
	case 'SlideUp':
		new Effect.SlideUp($('demonstracao'));
		break;
	case 'DropOut':
		new Effect.DropOut($('demonstracao'));
		break;
	case 'Shake':
		new Effect.Shake($('demonstracao'));
		break;
	case 'Pulsate':
		new Effect.Pulsate($('demonstracao'));
		break;
	case 'Squish':
		new Effect.Squish($('demonstracao'));
		break;
	case 'Fold':
		new Effect.Fold($('demonstracao'));
		break;
	case 'Grow':
		new Effect.Grow($('demonstracao'));
		break;
	case 'Shrink':
		new Effect.Shrink($('demonstracao'));
		break;
	case 'Highlight':
		new Effect.Highlight($('demonstracao'), {
			startcolor :'#FF0000',
			endcolor :'#0000FF',
			restorecolor :'#00FF00'
		});
		break;
	case 'toggle':
		new Effect.toggle($('demonstracao'), 'blind');
		break;

	}

}