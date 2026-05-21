export type PlaceCategory =
  | "museum"
  | "restaurant"
  | "cafe"
  | "viewpoint"
  | "neighborhood"
  | "other";

export interface Place {
  id: string;
  tripId: string;
  name: string;
  category: PlaceCategory;
  rating?: number; // 1–5
  notes?: string;
  address?: string;
  latitude?: number;
  longitude?: number;
  images?: string[];
  createdAt: string;
  updatedAt: string;
}

export interface Trip {
  id: string;
  userId: string;
  title: string;
  country: string;
  city: string;
  startDate: string;
  endDate: string;
  notes?: string;
  coverImage?: string;
  places?: Place[];
  createdAt: string;
  updatedAt: string;
}

export type CreateTripInput = Omit<
  Trip,
  "id" | "userId" | "places" | "createdAt" | "updatedAt"
>;

export type UpdateTripInput = Partial<CreateTripInput>;

export type CreatePlaceInput = Omit<
  Place,
  "id" | "tripId" | "createdAt" | "updatedAt"
>;

export type UpdatePlaceInput = Partial<CreatePlaceInput>;
