# Terra :earth_americas:

### Perlin Noise
  + Steps
    1. Grid definition
       1. Second level grid generation.
       2. Pseudorandom vector generation at each second level grid vertex.
    2. Dot Product calculation between each first level grid value and its four respective second level grid vertex vectors.
    3. Interpolation

##### Terminology

+ **Seed**: 
  + **Definition**: The single number utilized to initiate the pseudorandom generation of a noise map.
  + **Use**: Each pseudorandom noise map can be recreated from scratch through the reuse of its unique seed.
    |          Seed 0           |          Seed 1           |
    | :-----------------------: | :-----------------------: |
    | ![Seed: 0][perlin-seed-0] | ![Seed: 1][perlin-seed-1] |
+ Frequency: 
  + **Definition**:
  + **Use**: 
+ Octaves:
  + **Definition**:
  + **Use**: 
+ Persistence:
  + **Definition**:
  + **Use**: 
+ Lacunarity:
  + **Definition**:
  + **Use**: 
+ Noise Mask:
  + **Definition**:
  + **Use**: 

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

[perlin-seed-0]: https://i.imgur.com/B7FhPhV.png "Perlin Noise with seed 0."
[perlin-seed-1]: https://i.imgur.com/oJhRLLx.png "Perlin Noise with seed 1."