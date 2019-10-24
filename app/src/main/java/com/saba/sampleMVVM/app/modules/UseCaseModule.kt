package com.saba.sampleMVVM.app.modules

import com.saba.sampleMVVM.domain.useCases.*
import org.koin.dsl.module.module

object UseCaseModule {

    val module = module {
        
        factory {
            DropLocalReposUseCase(get())
        }
        factory {
            GetGlobalReposUseCase(get())
        }
        factory {
            GetLocalReposUseCase(get())
        }
        factory {
            SaveLocalRepoUseCase(get())
        }
        
    }
    
}