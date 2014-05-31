/*
 * Copyright 2013  Séven Le Mesle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package fr.xebia.xke.dagger;

import dagger.Module;
import dagger.Provides;
import fr.xebia.xke.dagger.model.Todo;
import fr.xebia.xke.dagger.repository.TodosMapRepository;
import fr.xebia.xke.dagger.repository.TodosRepository;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Created by slemesle on 31/05/2014.
 */
@Module(library = true, overrides = true)
public class TodosInMemoryModule {

    @Provides public Map<String, Todo> provideTodoMap(){
        return asList(
                new Todo("1234", "Titre 1", "lorem ipsum"),
                new Todo("5432", "Titre 2", "lorem ipsum"),
                new Todo("08758", "Titre 3", "lorem ipsum")
        ).stream().collect(Collectors.toMap(Todo::getId, todo -> todo));
    }

    @Provides
    TodosRepository provideTodosRepository(TodosMapRepository mongoRepository){
        return mongoRepository;
    }

}
