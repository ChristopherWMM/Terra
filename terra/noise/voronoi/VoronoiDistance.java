package terra.noise.voronoi;

/**
 * The accepted methodologies for calculating distance within a {@link VoronoiNoiseGenerator}.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public enum VoronoiDistance {
	Euclidean, Manhattan, Minkowski, Chebyshev, Equidistant
}
