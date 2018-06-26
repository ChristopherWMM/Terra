# Terra :earth_americas:

## Perlin Noise Overview

Perlin noise, while random, is what is referred to as *coherent noise* meaning there is gradual change between values. This can be clearly visualized by translating sections of a given noise map. When done to random noise, the section blends in immediately after its movement stops. However, when done to a coherent noise, like Perlin, the gradual change between values is interrupted and ends up being plainly obvious as seen below.

|          *White Noise*           |          *Perlin Noise*           |
| :-----------------------: | :-----------------------: |
| ![white-noise-example][white-noise-example] | ![perlin-noise-example][perlin-noise-example] |

#### Perlin Noise Algorithm

1. **Definition:**
   1. Perlin grid generation.

      ![pixel-grid][pixel-grid]

   2. Pseudorandom vector generation at each Perlin grid vertex.

      ![perlin-grid-vectors][perlin-grid-vectors]

2. **Calculation:** 
   1. Calculate the Dot Product between each pixel position and its four respective Perlin grid vertex vectors. This ensures that each point in adjacent Perlin grid quadrants shares two pseudorandom vectors.

      ![perlin-grid-dot-product][perlin-grid-dot-product]

3. **Interpolation:**
   1. Utilize bilinear interpolation to combine the four dot products.

      ![perlin-grid-interpolation][perlin-grid-interpolation]
      
   2. Utilize a sigmoid fade function, otherwise known as an ease curve to remove any seams found between Perlin grid quadrants.

   |          *No fading*           |          *Fading*           |
   | :-----------------------: | :-----------------------: |
   | ![fade-function-on][fade-function-off] | ![fade-function-on][fade-function-on] |

      + **Fade function used:** ![Fade Function][fade-function]

        ![fade-function-graph][fade-function-graph]

   2. Calculate the local minimum and maximum of the given noise map, then rescale the individual noise values back onto the interval of [0-1] through inverse linear interpolation.


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
  + **Use**: Allows for the compounding of noise layers such that subsequent layers add detail and roughness to the previous noise layers without changing their overall structure.

    | *Original Noise Layer*      | *Noise Layer 1*       | *Noise Layer 2* | *Resultant Noise Map* |
    | :-----------------------: | :-----------------------: | :-----------------------: | :-----------------------: |
    | ![Original Octave][octave-original] | ![Layer 1][octave-1] | ![Layer 2][octave-2] |  ![Octave Result][octave-result]|

+ Persistence:
  + **Definition**: The amplitude multiplier between subsequently noise map octaves.

  + **Amplitude function used:** ![Amplitude Function][amplitude-function]

  + **Use**: Controls the intensity and detail of the small features present on the noise map after all octaves have been compounded.

    | *Low Persistence*      | *Moderate Persistence*       | *High Persistence* |
    | :-----------------------: | :-----------------------: | :-----------------------: |
    | ![Low Persistence][persistence-low] | ![Moderate Persistence][persistence-moderate] | ![High Persistence][persistence-high] |

+ Lacunarity:
  + **Definition**: The frequency multiplier between subsequently noise map octaves.
  + **Frequency function used:** ![Frequency Function][frequency-function]
  + **Use**: Controls the number of small features visible on the noise map after all octaves have been compounded.

    | *Low Lacunarity* | *Moderate Lacunarity* | *High Lacunarity* |
    | :-----------------------: | :-----------------------: | :-----------------------: |
    | ![Low Lacunarity][lacunarity-low] | ![Moderate Lacunarity][lacunarity-moderate] | ![High Lacunarity][lacunarity-high] |

+ Noise Mask:
  + **Definition**: A topmost layer added to a noise map meant to conceal its noise values by a variable factor.
  + **Use**: Allows for specific areas of a noise map to be obscured or completely hidden.

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

[pixel-grid]: https://i.imgur.com/MzycPn3.png "Pixel Grid"
[perlin-grid-vectors]: https://i.imgur.com/AtokBzZ.png "Perlin Grid Vectors"
[perlin-grid-dot-product]: https://i.imgur.com/vLaYSQm.png "Perlin Grid Dot Product"
[perlin-grid-interpolation]: https://i.imgur.com/Aaclvcq.png "Perlin Grid Value Interpolation"

[fade-function-on]: https://i.imgur.com/G4yoUpG.png "Faded Perlin Noise"
[fade-function-off]: https://i.imgur.com/77pfRxu.png "Non-faded Perlin Noise"
[fade-function-graph]: https://i.imgur.com/jhFxziQ.png "The graph of the fade function utilized."
[fade-function]: http://latex.codecogs.com/gif.latex?f%28x%29%20%3D%206x%5E%7B5%7D%20-%2015x%5E%7B4%7D&amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;plus;10x%5E%7B3%7D "Perlin Noise fade function LaTeX."

[perlin-seed-x]: https://i.imgur.com/B7FhPhV.png "Perlin Noise with seed X."
[perlin-seed-y]: https://i.imgur.com/oJhRLLx.png "Perlin Noise with seed Y."

[perlin-frequency-1]: https://i.imgur.com/MwjNCIh.png "Perlin Noise with frequency 1."
[perlin-frequency-2]: https://i.imgur.com/OuX2vRD.png "Perlin Noise with frequency 2."
[perlin-frequency-4]: https://i.imgur.com/nj7vxrf.png "Perlin Noise with frequency 4."

[octave-original]: https://i.imgur.com/SitIQgd.png "Original Perlin Noise."
[octave-1]: https://i.imgur.com/2UpSDMy.png "Perlin Noise Layer 1."
[octave-2]: https://i.imgur.com/OaMuuOo.png "Perlin Noise Layer 2."
[octave-result]: https://i.imgur.com/O4MkDFe.png "Resultant Perlin Noise after Octave compounding."

[persistence-low]: https://i.imgur.com/M6HKvDd.png "Low Persistence Perlin Noise."
[persistence-moderate]: https://i.imgur.com/kbPvufi.png "Moderate Persistence Perlin Noise."
[persistence-high]: https://i.imgur.com/n5LvPEV.png "High Persistence Perlin Noise."

[lacunarity-low]: https://i.imgur.com/94DskyS.png "Low Lacunarity Perlin Noise."
[lacunarity-moderate]: https://i.imgur.com/WnQSBM2.png "Moderate Lacunarity Perlin Noise."
[lacunarity-high]: https://i.imgur.com/94jQRU6.png "High Lacunarity Perlin Noise."

[amplitude-function]: http://latex.codecogs.com/gif.latex?amplitude%20%3D%20persistence%7B%5E%7B%28octaves%20-%201%29%7D%7D "Perlin Noise persistence function LaTeX."

[frequency-function]: http://latex.codecogs.com/gif.latex?frequency%20%3D%20%28initial%20frequency%29%20*%20lacunarity%7B%5E%7B%28octaves%20-%201%29%7D%7D "Perlin Noise frequency function LaTeX."

[noise-mask]: https://i.imgur.com/HgLPvlF.png "Raw Noise Mask."
[raw-perlin]: https://i.imgur.com/OXBXLNm.png "Perlin Noise with no Noise Mask."
[masked-perlin]: https://i.imgur.com/v9pjZlY.png "Perlin Noise with a Noise Mask."
