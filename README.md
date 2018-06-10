# Terra :earth_americas:

## Perlin Noise Overview

Perlin noise, while random, is what is referred to as *coherent noise* meaning there is gradual change between values. This can be clearly visualized by translating sections of a given noise map. When done to random noise, the section blends in immediately after its movement stops. However, when done to a coherent noise, like Perlin, the gradual change between values is interrupted and ends up being plainly obvious as seen below.

|          *White Noise*           |          *Perlin Noise*           |
| :-----------------------: | :-----------------------: |
| ![white-noise-example][white-noise-example] | ![perlin-noise-example][perlin-noise-example] |

#### Perlin Noise Algorithm

1. **Definition:**
   1. Perlin grid generation.
   2. Pseudorandom vector generation at each Perlin grid vertex.
2. **Calculation:** 
   1. Calculate the Dot Product between each pixel position and its four respective Perlin grid vertex vectors. This ensures that each point in adjacent Perlin grid quadrants share two pseudorandom vectors.
3. **Interpolation:**
   1. Utilize a sigmoid fade function, otherwise known as an ease curve to remove any seams found between Perlin grid quadrants.
      + **Fade function used:** ![Fade Function][fade-function]
   2. Calculate the local minimum and maximum of the given noise map, then rescale the individual noise values back onto the interval of [0-1] through inverse linear interpolation.
      ​

##### Terminology

+ **Seed**: 
  + **Definition**: The single number utilized to initiate the pseudorandom generation of a noise map.
  + **Use**: Each pseudorandom noise map can be recreated from scratch through the reuse of its unique seed.

|          *Seed X*           |          *Seed Y*           |
| :-----------------------: | :-----------------------: |
| ![Seed: x][perlin-seed-x] | ![Seed: y][perlin-seed-y] |

+ Frequency: 
  + **Definition**: The scale number of individual peaks and troughs found in each dimension of a single noise map octave.
  + **Use**: Controls the initial scale along the x and y axes of the Perlin noise map. As seen below, increasing the initial frequency by a factor of two causes the previous noise map to be present, but only represent a quarter of the subsequent grid.

|          *Frequency: 1*           |          *Frequency: 2*           | *Frequency: 4* |
| :-----------------------: | :-----------------------: | :-----------------------: |
| ![Frequency: 1][perlin-frequency-1] | ![Frequency: 2][perlin-frequency-2] | ![Frequency: 4][perlin-frequency-4] |
###### \* Note: The sigmoid fade function has been disabled to clearly show the effect of increasing the initial frequency. This causes artifacts to be visible between Perlin grid quadrants.

+ Octave:
  + **Definition**: A single noise map which can be used independently or in compounding layers.
  + **Use**: 
    ​
+ Persistence:
  + **Definition**: The amplitude multiplier between subsequently noise map octaves.
  + **Amplitude function used:** ![Amplitude Function][amplitude-function]
  + **Use**: 
    ​
+ Lacunarity:
  + **Definition**: The frequency multiplier between subsequently noise map octaves.
  + **Frequency function used:** ![Frequency Function][frequency-function]
  + **Use**: 
    ​
+ Noise Mask:
  + **Definition**: A topmost layer added to a noise map meant to conceal its noise values by a variable factor.
  + **Use**: 

|          *Noise Mask*       |          *Raw Noise*       | *Masked Noise* |
| :-----------------------: | :-----------------------: | :-----------------------: |
| ![Noise Mask][noise-mask] | ![Raw Perlin][raw-perlin] | ![Masked Perlin][masked-perlin] |

-----
#### Generating Perlin Noise

```java
Noise perlin = new PerlinNoiseGenerator()
			// The desired (positive, non-zero, integer) height of the generated PerlinNoise object.
    		.height(512)
			// The desired (positive, non-zero, integer) width of the generated PerlinNoise object.
    		.width(512)
			// The desired (long) seed used when generating the PerlinNoise object.
    		.seed(0)
			// The desired (positive, non-zero integer) initial frequency of the generated PerlinNoise object.
    		.frequency(1)
			// The desired (positive, non-zero, integer) number of octaves present in the generated PerlinNoise object.
    		.octaves(10)
			// The desired (positive, non-zero, double) persistence of the generated PerlinNoise object.
    		.persistence(0.5)
			// The desired (positive, non-zero, double) lacunarity of the generated PerlinNoise object.
    		.lacunarity(2.8)
			// The desired (positive, [0-1], double) intensity of the NoiseMask being applied to the generated PerlinNoise object.
    		.noiseMask(0.5)
			.generate();
```

[white-noise-example]: https://i.imgur.com/kdvoLXs.gif "White Noise"
[perlin-noise-example]: https://i.imgur.com/ZIbyS0g.gif "Perlin Noise"

[fade-function]: http://latex.codecogs.com/gif.latex?f%28x%29%20%3D%206x%5E%7B5%7D%20-%2015x%5E%7B4%7D&amp;amp;plus;10x%5E%7B3%7D "Perlin Noise fade function LaTeX."

[perlin-seed-x]: https://i.imgur.com/B7FhPhV.png "Perlin Noise with seed X."
[perlin-seed-y]: https://i.imgur.com/oJhRLLx.png "Perlin Noise with seed Y."

[perlin-frequency-1]: https://i.imgur.com/MwjNCIh.png "Perlin Noise with frequency 1."
[perlin-frequency-2]: https://i.imgur.com/OuX2vRD.png "Perlin Noise with frequency 2."
[perlin-frequency-4]: https://i.imgur.com/nj7vxrf.png "Perlin Noise with frequency 4."

[amplitude-function]: http://latex.codecogs.com/gif.latex?amplitude%20%3D%20persistence%7B%5E%7B%28octaves%20-%201%29%7D%7D "Perlin Noise persistence function LaTeX."

[frequency-function]: http://latex.codecogs.com/gif.latex?frequency%20%3D%20%28initial%20frequency%29%20*%20lacunarity%7B%5E%7B%28octaves%20-%201%29%7D%7D "Perlin Noise frequency function LaTeX."

[noise-mask]: https://i.imgur.com/HgLPvlF.png "Raw Noise Mask."
[raw-perlin]: https://i.imgur.com/OXBXLNm.png "Perlin Noise with no Noise Mask."
[masked-perlin]: https://i.imgur.com/v9pjZlY.png "Perlin Noise with a Noise Mask."
