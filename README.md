# Terra :earth_americas:

## Perlin Noise Overview

Perlin noise, while random, is what is referred to as coherent meaning there is gradual change between values.

|          *White Noise*           |          *Perlin Noise*           |
| :-----------------------: | :-----------------------: |
| ![white-noise-example][white-noise-example] | ![perlin-noise-example][perlin-noise-example] |

#### Perlin Noise Algorithm

1. **Definition:**
   1. Second level grid generation.
   2. Pseudorandom vector generation at each second level grid vertex.
2. **Calculation:** 
   1. Calculate the Dot Product between each first level grid value and its four respective second level grid vertex vectors. This ensures that each point in adjacent second level grid quadrants share two vectors.
3. **Interpolation:**
   1. Utilize a sigmoid fade function, otherwise known as an ease curve to remove any seams found between second level grid quadrants.
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
  + **Use**: Controls the initial x and y scale of the noise.

|          *Frequency: 1*           |          *Frequency: 2*           | *Frequency: 4* |
| :-----------------------: | :-----------------------: | :-----------------------: |
| ![Frequency: 1][perlin-frequency-1] | ![Frequency: 2][perlin-frequency-2] | ![Frequency: 4][perlin-frequency-4] |
###### 	\*Note: Linear interpolation has been disabled to clearly show the effect of increasing the initial frequency. This causes artifacts to be visible between second level grid tiles.

+ Octave:
  + **Definition**: A single noise map which can be used independently or in compounding layers.
  + **Use**: 
    ​
+ Persistence:
  + **Definition**: The amplitude multiplier between subsequently noise map octaves.
  + **Use**: 
    ​
+ Lacunarity:
  + **Definition**: The frequency multiplier between subsequently noise map octaves.
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
			.height(512)
			.width(512)
			.seed(0)
			.frequency(1)
			.octaves(10)
			.persistence(0.5)
			.lacunarity(2.8)
			.noiseMask(0.5)
			.generate();
```

[white-noise-example]: https://i.imgur.com/kdvoLXs.gif
[perlin-noise-example]: https://i.imgur.com/ZIbyS0g.gif
[fade-function]: http://latex.codecogs.com/gif.latex?%246x%5E5-15x%5E4&amp;amp;amp;amp;amp;plus;10x%5E3%24

[perlin-seed-x]: https://i.imgur.com/B7FhPhV.png "Perlin Noise with seed X."
[perlin-seed-y]: https://i.imgur.com/oJhRLLx.png "Perlin Noise with seed Y."

[perlin-frequency-1]: https://i.imgur.com/MwjNCIh.png "Perlin Noise with frequency 1."
[perlin-frequency-2]: https://i.imgur.com/OuX2vRD.png "Perlin Noise with frequency 2."
[perlin-frequency-4]: https://i.imgur.com/nj7vxrf.png "Perlin Noise with frequency 4."

[noise-mask]: https://i.imgur.com/HgLPvlF.png "Raw Noise Mask."
[raw-perlin]: https://i.imgur.com/OXBXLNm.png "Perlin Noise with no Noise Mask."
[masked-perlin]: https://i.imgur.com/v9pjZlY.png "Perlin Noise with a Noise Mask."
