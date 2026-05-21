import type { CreateTripInput, Trip, UpdateTripInput } from "@/types/trip";
import { apiClient } from "./client";

export const tripsApi = {
  getAll: async (): Promise<Trip[]> => {
    const { data } = await apiClient.get<Trip[]>("/trips");
    return data;
  },

  getById: async (id: string): Promise<Trip> => {
    const { data } = await apiClient.get<Trip>(`/trips/${id}`);
    return data;
  },

  create: async (input: CreateTripInput): Promise<Trip> => {
    const { data } = await apiClient.post<Trip>("/trips", input);
    return data;
  },

  update: async (id: string, input: UpdateTripInput): Promise<Trip> => {
    const { data } = await apiClient.patch<Trip>(`/trips/${id}`, input);
    return data;
  },

  delete: async (id: string): Promise<void> => {
    await apiClient.delete(`/trips/${id}`);
  },
};
