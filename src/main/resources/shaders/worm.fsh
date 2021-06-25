#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

void main( void ) {

	vec3 Color = vec3(0.1, 0.3, 0.9);
	float col = -0.1;
	vec2 a = (gl_FragCoord.xy * 2.0 - resolution) / min(resolution, resolution.y);
	for(float i=0.0;i<50.0;i++)
	{
	  float si = sin(time + i * 0.05)/0.5;
	  float co = cos(time + i * 0.05)*0.5;
	  col += 0.01 / abs(length(a + vec2(si , co * si )) - 0.1);
	}
	gl_FragColor = vec4(vec3(Color * col), 1.0);
}